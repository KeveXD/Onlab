package hu.bme.aut.android.proba3.main.expens_income

import android.R
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import hu.bme.aut.android.proba3.databinding.ActivityQueryBinding

import hu.bme.aut.android.proba3.main.data.RepositoryExpenses
import hu.bme.aut.android.proba3.main.querys.AdapterQuery
import hu.bme.aut.android.proba3.main.querys.ViewModelQuery
import kotlin.concurrent.thread

class ActivityQuery : AppCompatActivity(), AdapterQuery.AdapterInterface {

    private lateinit var viewModelQuery: ViewModelQuery
    private lateinit var adapter: AdapterQuery
    private var isLLQueryExpanded = false
    private var initialQueryHeight = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityQueryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val database: RepositoryExpenses = RepositoryExpenses.getDatabase(applicationContext)
        val pocketSpinner: Spinner = binding.pocketSpinner

        // Init Adapter
        adapter = AdapterQuery(this)
        binding.myRecyclerView.adapter = adapter

        // Init ViewModel
        viewModelQuery = ViewModelProvider(this).get(ViewModelQuery::class.java)
        viewModelQuery.adapter = adapter
        viewModelQuery.database = database
        viewModelQuery.context = this

        initialQueryHeight = binding.llQuery.height

        binding.bGo.setOnClickListener {
            if (!isLLQueryExpanded) {
                binding.llQuery.animate().translationY(-initialQueryHeight.toFloat())
                isLLQueryExpanded = true
            } else {
                binding.llQuery.animate().translationY(0f)
                isLLQueryExpanded = false
            }
        }

        thread {
            val pocketsList = viewModelQuery.getPockets()

            runOnUiThread {
                val pocketAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, pocketsList)
                pocketAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                pocketSpinner.adapter = pocketAdapter
                pocketSpinner.onItemSelectedListener = onItemSelectedListener

                binding.editText1.setOnClickListener {
                    binding.pocketSpinner.performClick()
                }
            }
        }


        viewModelQuery.loadItemsInBackground()
    }


    private val onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
            // selected pocket is pockets[position]
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            // do nothing
        }
    }
}
