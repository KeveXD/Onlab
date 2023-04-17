package hu.bme.aut.android.proba3.main.debt.details

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.proba3.R
import hu.bme.aut.android.proba3.databinding.ActivityDebtDetailsBinding

import hu.bme.aut.android.proba3.main.debt.data.DatabaseDebt
import hu.bme.aut.android.proba3.main.debt.data.DebtItem

lateinit var viewModel: ViewModelDetails


class Detailes : AppCompatActivity(), AdapterDetails.mainFigyeloInterface,ViewModelDetails.ContactDebt
    {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= ViewModelProvider(this).get(ViewModelDetails::class.java)
        val binding: ActivityDebtDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_debt_details)
        val adapter: AdapterDetails = AdapterDetails(this)
        val database: DatabaseDebt=DatabaseDebt.getDatabase(applicationContext)

        val szamol: Button =findViewById(R.id.bSzamol)

        //init viewModel
        binding.viewmodel=viewModel
        viewModel.adapter=adapter
        viewModel.context=this
        viewModel.database=database

        szamol.setOnClickListener{

            viewModel.initKulcsok()
            viewModel.szamol()
            //println("Tomb elemszama: %d".format(adapter.tartozasok.size))
            viewModel.updateItems()

        }

        //Init RecicleView
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter

        //betolti az adatbazisbol a rekordokat
        viewModel.loadItemsInBackground()

    }


    override fun onItemChanged(item: DebtItem) {

    }

    override fun onItemDelete(item: DebtItem, position: Int) {

    }
}