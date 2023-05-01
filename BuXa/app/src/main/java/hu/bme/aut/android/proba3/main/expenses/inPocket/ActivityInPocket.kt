package hu.bme.aut.android.proba3.main.expenses.inPocket

import FragmentExpenses
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
lateinit var binding: ActivityInpocketBinding
class ActivityInPocket : AppCompatActivity(), AdapterInPocket.AdapterInterface,
    ViewModelInPocket.mainFigyelo, FragmentExpenses.FragmentInterface, FragmentExpensesModify.FragmentInterface2 {

    private lateinit var viewModelExpenses: ViewModelInPocket
    private lateinit var adapter: AdapterInPocket
    private var name: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_inpocket)
        val database: DatabaseExpenses = DatabaseExpenses.getDatabase(applicationContext)
        val plusz: FloatingActionButton =findViewById(R.id.plusz_gomb)

        //A pocket nevet veszi at
        name = intent.getStringExtra("name2")
        binding.tvPocket.text=name.toString()

        // Adapter létrehozása és inicializálása
        adapter = AdapterInPocket(name,this)

        // ViewModel inicializálása
        viewModelExpenses= ViewModelProvider(this).get(ViewModelInPocket::class.java)
        viewModelExpenses.adapter=adapter
        viewModelExpenses.database=database
        viewModelExpenses.context=this
        viewModelExpenses.pocketName=name

        //pluszGomb megnyit egy fragmentet
        plusz.setOnClickListener{
            FragmentExpenses(name).show(
                supportFragmentManager,
                "PaymentFragment"
            )
        }



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

    override fun sum(p: Int) {
        binding.tvAmount.text=p.toString()
    }

    override fun openFragmentModify(item: ExpensItem) {
        FragmentExpensesModify(item,name).show(
            supportFragmentManager,
            "PaymentFragment"
        )
    }


}
