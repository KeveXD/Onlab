package hu.bme.aut.android.proba3.main.expens_income

import android.R
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import hu.bme.aut.android.proba3.databinding.ActivityQueryBinding
import hu.bme.aut.android.proba3.main.data.RepositoryExpenses
import hu.bme.aut.android.proba3.main.querys.AdapterQuery
import hu.bme.aut.android.proba3.main.querys.ViewModelQuery
import java.util.*

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

        viewModelQuery.getPockets { pocketsList ->
            val pocketAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, pocketsList)
            pocketAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.pocketSpinner.adapter = pocketAdapter
            binding.pocketSpinner.onItemSelectedListener = onItemSelectedListener

            binding.editText1.setOnClickListener {
                binding.pocketSpinner.performClick()
            }
        }

        binding.button1.setOnClickListener {
            showDatePicker(binding.textView1,"Kezdődátum")
        }

        binding.button2.setOnClickListener {
            showDatePicker(binding.textView2,"Végsődátum")
        }

        viewModelQuery.loadItemsInBackground()
    }


    private val onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
        }
    }

    private fun showDatePicker(textView: TextView, pre: String) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                textView.text = "$pre: $year-${monthOfYear+1}-$dayOfMonth"
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }
}


