package hu.bme.aut.android.proba3.main.expenses.inPocket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hu.bme.aut.android.proba3.R
import hu.bme.aut.android.proba3.databinding.ActivityInpocketBinding
import hu.bme.aut.android.proba3.main.debt.AdapterDebt
import hu.bme.aut.android.proba3.main.debt.FragmentDebt
import hu.bme.aut.android.proba3.main.debt.ViewModelDebt
import hu.bme.aut.android.proba3.main.debt.data.DatabaseDebt
import hu.bme.aut.android.proba3.main.debt.data.DebtItem
import hu.bme.aut.android.proba3.main.debt.viewModel
import hu.bme.aut.android.proba3.main.expenses.data.DatabaseExpenses
import hu.bme.aut.android.proba3.main.expenses.data.ExpensItem

lateinit var viewModelExpenses: ViewModelInPocket
class ActivityInPocket : AppCompatActivity(), AdapterInPocket.AdapterInterface,
ViewModelInPocket.mainFigyelo,FragmentExpenses.FragmentInterface{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityInpocketBinding = DataBindingUtil.setContentView(this, R.layout.activity_inpocket)
        viewModelExpenses= ViewModelProvider(this).get(ViewModelInPocket::class.java)
        val adapter: AdapterInPocket = AdapterInPocket(this)
        val database: DatabaseExpenses = DatabaseExpenses.getDatabase(applicationContext)
        val plusz: FloatingActionButton =findViewById(R.id.plusz_gomb)

        //init viewModelExpenses
        binding.viewmodel=viewModelExpenses
        viewModelExpenses.adapter=adapter
        viewModelExpenses.database=database
        viewModelExpenses.context=this

        //megnyit egy fragmentet
        plusz.setOnClickListener{
            FragmentExpenses().show(
                supportFragmentManager,
                "PaymentFragment"
            )
        }

        //A pocket nevet veszi at
        val name = intent.getStringExtra("name2")
        binding.tvPocket.text=name.toString()

        //Init RecicleView
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        viewModelExpenses.loadItemsInBackground()
    }



    override fun newPaymentCreated(newItem: ExpensItem) {
        viewModelExpenses.newPaymentCreated(newItem)
    }

    override fun onItemDelete(item: ExpensItem, position: Int) {
        viewModelExpenses.onItemDelete(item,position)
    }

}
