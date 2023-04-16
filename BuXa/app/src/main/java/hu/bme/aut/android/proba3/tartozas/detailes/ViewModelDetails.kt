package hu.bme.aut.android.proba3.tartozas.detailes

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import hu.bme.aut.android.proba3.tartozas.AdapterDebt

import hu.bme.aut.android.proba3.tartozas.data.DatabaseDebt
import hu.bme.aut.android.proba3.tartozas.data.DebtItem
import kotlin.concurrent.thread



class ViewModelDetails(): ViewModel() {

    lateinit var database: DatabaseDebt
    lateinit var adapter: AdapterDetails

    lateinit var context: Context

    private val kulcsok = mutableListOf<Kulcs>()


    fun loadItemsInBackground() {
        thread {
            val items =database.DatabaseDebtFun().getAll().toMutableList()
            (context as Activity).runOnUiThread {
                adapter.update(items)
            }
        }
    }
    fun updateItems() {
        thread {
            val items = adapter.tartozasok//database.DatabaseDebtFun().getAll().toMutableList()
            (context as Activity).runOnUiThread {
                adapter.update(items)
            }
        }
    }


    fun initKulcsok(){
        //megnezzuk minden l elemrol hogy benne van e mar a kulcsok listaba
        //ha nincs beletesszuk
        //minden l elemben 2 nev van ezert van 2 bool valtozo
        println("kulcsok szama: %d".format(kulcsok.size))
        for (l in adapter.tartozasok)
        {
            if (!l.isPayed)
            {
                var bWhom = true
                var bWho = true
                for (k in kulcsok) {
                    if (k.nev == l.who || l.who == l.whom)
                        bWho = false
                    if (k.nev == l.whom)
                        bWhom = false
                }
                if (bWho)
                    kulcsok.add(Kulcs(l.who))
                if (bWhom)
                    kulcsok.add(Kulcs(l.whom))
            }
        }
        //A listaban bent vannak a nevek
        //mindenki neve  egyszer szerepel a listaban

        //a kulcsok tombbe mindenkihez feltoltjuk az osszesen mezot azzal a szammal,
        //amennyivel a sajat penztarcajanak novekednie kell. Ha o tartozik, akkor
        //negativ szam kerul beirasra
        for (l in adapter.tartozasok)
        {
            if (!l.isPayed)
            {
                for (k in kulcsok) {
                    if (l.who == k.nev)
                        k.osszesen -= l.amount
                    if (l.whom == k.nev)
                        k.osszesen += l.amount
                }
            }
        }
    }

    fun szamol() {
        println("Szamolon beluuul kulcsok szama: %d".format(kulcsok.size))
        adapter.tartozasok.clear()
        var maxi=1//Legtobbet fizetnek neki
        var mini=1//Legtobbet kell fizetnie

        while(mini!=0 && maxi!=0){
            mini=0
            maxi=0
            for (kk in kulcsok)
            {
                if (maxi<kk.osszesen)
                    maxi=kk.osszesen
                if (mini>kk.osszesen)
                    mini=kk.osszesen
            }

            if (maxi!=0 && mini!=0)
            {
                for (i in kulcsok)
                {
                    if(maxi==i.osszesen)
                    {
                        for (ii in kulcsok)
                        {
                            if (mini==ii.osszesen)
                            {
                                if (-mini>=maxi){
                                    adapter.tartozasok.add( DebtItem(who=ii.nev,
                                        whom=i.nev,
                                        description = "",
                                        amount = i.osszesen,
                                        isPayed= false
                                    )
                                    )
                                    ii.osszesen+=i.osszesen
                                    i.osszesen=0
                                }

                                else if (-mini<maxi)
                                {
                                    adapter.tartozasok.add( DebtItem(who=ii.nev,
                                        whom=i.nev,
                                        description = "",
                                        amount =-ii.osszesen,
                                        isPayed= false
                                    ))
                                    i.osszesen+=ii.osszesen
                                    ii.osszesen=0

                                }
                            }
                        }
                    }
                }
            }
        }
        println("tartozasok szama: %d".format(adapter.tartozasok.size))
    }



    interface ContactDebt {
    }


}