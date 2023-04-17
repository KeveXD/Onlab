package hu.bme.aut.android.proba3.main.tartozas.detailes

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel

import hu.bme.aut.android.proba3.main.tartozas.data.DatabaseDebt
import hu.bme.aut.android.proba3.main.tartozas.data.DebtItem
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
            val items = adapter.tartozasok.toMutableList()//database.DatabaseDebtFun().getAll().toMutableList()
            (context as Activity).runOnUiThread {
                adapter.update(items)
            }
        }
    }


    fun initKulcsok() {
        adapter.tartozasok.filterNot { it.isPayed }.forEach { l ->
            val bWhom = kulcsok.none { it.nev == l.whom }
            val bWho = kulcsok.none { it.nev == l.who || l.who == l.whom }
            if (bWho) kulcsok.add(Kulcs(l.who))
            if (bWhom) kulcsok.add(Kulcs(l.whom))
        }
        adapter.tartozasok.filterNot { it.isPayed }.forEach { l ->
            kulcsok.filter { it.nev == l.who }.forEach { it.osszesen -= l.amount }
            kulcsok.filter { it.nev == l.whom }.forEach { it.osszesen += l.amount }
        }
    }


    fun szamol() {
        adapter.tartozasok.clear()
        var (maxi, mini) = Pair(1, 1)

        while (mini != 0 && maxi != 0) {
            mini = 0
            maxi = 0

            kulcsok.forEach {
                maxi = maxOf(maxi, it.osszesen)
                mini = minOf(mini, it.osszesen)
            }

            if (maxi != 0 && mini != 0) {
                kulcsok.find { it.osszesen == maxi }?.let { i ->
                    kulcsok.find { it.osszesen == mini }?.let { ii ->
                        when {
                            -mini >= maxi -> {
                                adapter.tartozasok.add(DebtItem(
                                    who = ii.nev,
                                    whom = i.nev,
                                    description = "",
                                    amount = i.osszesen,
                                    isPayed = false
                                ))
                                ii.osszesen += i.osszesen
                                i.osszesen = 0
                            }
                            -mini < maxi -> {
                                adapter.tartozasok.add(DebtItem(
                                    who = ii.nev,
                                    whom = i.nev,
                                    description = "",
                                    amount = -ii.osszesen,
                                    isPayed = false
                                ))
                                i.osszesen += ii.osszesen
                                ii.osszesen = 0
                            }
                        }
                    }
                }
            }
        }
    }




    interface ContactDebt {
    }


}