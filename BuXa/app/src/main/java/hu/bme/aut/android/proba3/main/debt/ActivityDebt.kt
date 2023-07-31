package hu.bme.aut.android.proba3.main.debt

import Proba
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hu.bme.aut.android.proba3.ProbaKetto

import hu.bme.aut.android.proba3.R
import hu.bme.aut.android.proba3.databinding.DebtActivityBinding
import hu.bme.aut.android.proba3.main.debt.data.RepositoryDebt
import hu.bme.aut.android.proba3.main.debt.data.DebtItem

lateinit var viewModel: ViewModelDebt
class FunctionDebt : AppCompatActivity(), AdapterDebt.AdapterInterface,
    FragmentDebt.FragmentInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= ViewModelProvider(this).get(ViewModelDebt::class.java)
        val binding: DebtActivityBinding = DataBindingUtil.setContentView(this, R.layout.debt_activity)
        val adapter =AdapterDebt(this)
        val database: RepositoryDebt=RepositoryDebt.getDatabase(applicationContext)
        val plusz: FloatingActionButton = binding.pluszGomb
        val tovabb: Button = binding.bTovabb
        val addName: Button = binding.bAddName


        //init viewModel
        binding.viewmodel=viewModel
        viewModel.adapter=adapter
        viewModel.database=database
        viewModel.context=this


        //megnyit egy fragmentet
        plusz.setOnClickListener{
            FragmentDebt().show(
                supportFragmentManager,
                "PaymentFragment"
            )
        }

        //tovabbmegy a szamolas ablakra (details)
        tovabb.setOnClickListener{
            val intent = Intent(this, ProbaKetto::class.java)
            startActivity(intent)
        }

        //Init RecicleView
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        viewModel.loadItemsInBackground()
    }

    //AdapterDebt interface-ben van
    override fun onItemDelete(item: DebtItem, position: Int) {
        viewModel.onItemDelete(item,position)
    }

    //FragmentDebt interface-ben van
    //az OK gomb lenyomásánál ez hívódik meg
    override fun newPaymentCreated(newItem: DebtItem) {
        viewModel.newPaymentCreated(newItem)
    }
}




