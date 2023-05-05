package hu.bme.aut.android.proba3.main.expens_income

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import hu.bme.aut.android.proba3.databinding.ActivityQueryBinding
import hu.bme.aut.android.proba3.main.data.ExpensItem
import hu.bme.aut.android.proba3.main.data.RepositoryExpenses
import hu.bme.aut.android.proba3.main.querys.AdapterQuery
import hu.bme.aut.android.proba3.main.querys.ViewModelQuery


class ActivityQuery : AppCompatActivity(),AdapterQuery.AdapterInterface {

    private lateinit var viewModelQuery: ViewModelQuery
    private lateinit var adapter: AdapterQuery
    private var isLLQueryExpanded = false
    private var initialQueryHeight = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityQueryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val database: RepositoryExpenses = RepositoryExpenses.getDatabase(applicationContext)

        // Init Adapter
        adapter = AdapterQuery(this)

        // Init ViewModel
        viewModelQuery= ViewModelProvider(this).get(ViewModelQuery::class.java)
        viewModelQuery.adapter=adapter
        viewModelQuery.database=database
        viewModelQuery.context=this


        initialQueryHeight = binding.llQuery.height

        binding.bGo.setOnClickListener {
            if (!isLLQueryExpanded) {
                //?????????????????????????????????????????????????
                binding.llQuery.animate().translationY(-initialQueryHeight.toFloat())
                isLLQueryExpanded = true
            } else {

                binding.llQuery.animate().translationY(0f)
                isLLQueryExpanded = false
            }
        }

        viewModelQuery.loadItemsInBackground()
    }

    override fun onItemDelete(item: ExpensItem, position: Int) {
        println("onItemDelete")    }

    override fun setSum(p: Int) {
        println("SEEEETSUUM")
    }

    override fun openFragmentModify(item: ExpensItem) {
        println("openFragmentModify")

    }
}




