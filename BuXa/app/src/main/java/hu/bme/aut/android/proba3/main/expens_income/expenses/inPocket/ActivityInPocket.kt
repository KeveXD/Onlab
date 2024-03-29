package hu.bme.aut.android.proba3.main.expens_income.expenses.inPocket

import FragmentExpenses
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hu.bme.aut.android.proba3.R
import hu.bme.aut.android.proba3.databinding.ActivityInpocketBinding
import hu.bme.aut.android.proba3.main.data.RepositoryExpenses
import hu.bme.aut.android.proba3.main.data.ExpensItem

lateinit var viewModelExpenses: ViewModelInPocket
lateinit var binding: ActivityInpocketBinding
class ActivityInPocket : AppCompatActivity(), AdapterInPocket.AdapterInterface,
     FragmentExpenses.FragmentInterface, FragmentExpensesModify.FragmentInterface2 {

    private lateinit var viewModelExpenses: ViewModelInPocket
    private lateinit var adapter: AdapterInPocket
    private var pocketName: String? = null
    private var callerName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //valamert nem jo???????????????????????????????????????????????????????????????????????
        getSupportActionBar()?.hide();

        binding= DataBindingUtil.setContentView(this, R.layout.activity_inpocket)
        val database: RepositoryExpenses = RepositoryExpenses.getDatabase(applicationContext)
        val plusz: FloatingActionButton =findViewById(R.id.plusz_gomb)

        //A pocket nevet veszi at
        pocketName = intent.getStringExtra("name2")
        //a hivo funkcio nevet veszi at
        callerName = intent.getStringExtra("caller")
        binding.tvPocket.text=pocketName.toString()

        // Init Adapter
        adapter = AdapterInPocket(pocketName,this)

        // Init ViewModel
        viewModelExpenses= ViewModelProvider(this).get(ViewModelInPocket::class.java)
        viewModelExpenses.adapter=adapter
        viewModelExpenses.database=database
        viewModelExpenses.context=this
        viewModelExpenses.pocketName=pocketName

        //pluszGomb
        plusz.setOnClickListener{
            FragmentExpenses(pocketName,callerName).show(
                supportFragmentManager,
                "PaymentFragment"
            )
        }

        //Init RecicleView
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        //?????????????????????????????????
        viewModelExpenses.sortPaymentsByDate()
        viewModelExpenses.loadItemsInBackground()


    }

    override fun addItem(newItem: ExpensItem) {
        viewModelExpenses.addItem(newItem)
    }

    override fun onItemDelete(item: ExpensItem, position: Int) {
        viewModelExpenses.onItemDelete(item,position)
    }

    override fun setSumMoney(p: Int) {
        binding.tvHowMuchMoney.text=p.toString()
    }

    override fun setSpentMoney(p: Int) {
        binding.tvHOwMuchMonySpent.text=p.toString()
    }

    override fun openFragmentModify(item: ExpensItem) {
        FragmentExpensesModify(item,pocketName,callerName).show(
            supportFragmentManager,
            "PaymentFragment"
        )
    }




}
