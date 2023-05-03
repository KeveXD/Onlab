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
import hu.bme.aut.android.proba3.main.expens_income.data.RepositoryExpenses
import hu.bme.aut.android.proba3.main.expens_income.data.ExpensItem

lateinit var viewModelExpenses: ViewModelInPocket
lateinit var binding: ActivityInpocketBinding
class ActivityInPocket : AppCompatActivity(), AdapterInPocket.AdapterInterface,
    ViewModelInPocket.mainObserver, FragmentExpenses.FragmentInterface, FragmentExpensesModify.FragmentInterface2 {

    private lateinit var viewModelExpenses: ViewModelInPocket
    private lateinit var adapter: AdapterInPocket
    private var name: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //valamert nem jo
        getSupportActionBar()?.hide();

        binding= DataBindingUtil.setContentView(this, R.layout.activity_inpocket)
        val database: RepositoryExpenses = RepositoryExpenses.getDatabase(applicationContext)
        val plusz: FloatingActionButton =findViewById(R.id.plusz_gomb)

        //A pocket nevet veszi at
        name = intent.getStringExtra("name2")
        binding.tvPocket.text=name.toString()

        // Init Adapter
        adapter = AdapterInPocket(name,this)

        // Init ViewModel
        viewModelExpenses= ViewModelProvider(this).get(ViewModelInPocket::class.java)
        viewModelExpenses.adapter=adapter
        viewModelExpenses.database=database
        viewModelExpenses.context=this
        viewModelExpenses.pocketName=name

        //pluszGomb
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

    override fun addItem(newItem: ExpensItem) {
        viewModelExpenses.addItem(newItem)
    }

    override fun onItemDelete(item: ExpensItem, position: Int) {
        viewModelExpenses.onItemDelete(item,position)
    }

    override fun setSum(p: Int) {
        binding.tvHOwMuchMonySpent.text=p.toString()
    }

    override fun openFragmentModify(item: ExpensItem) {
        FragmentExpensesModify(item,name).show(
            supportFragmentManager,
            "PaymentFragment"
        )
    }




}
