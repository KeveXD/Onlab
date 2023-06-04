package hu.bme.aut.android.proba3.main.expens_income

import android.R
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter

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

    private var startDate: String? = null
    private var endDate: String? = null
    private lateinit var pocketName: String
    private lateinit var monyFromWhere: String
    private lateinit var notes: String

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

        //mehet gomb
        binding.bGo.setOnClickListener {
            pocketName = binding.pocketSpinner.selectedItem.toString()
            monyFromWhere = binding.editText2.text?.toString() ?: ""
            notes = binding.editText3.text?.toString() ?: ""

            viewModelQuery.filterItems(startDate, endDate, pocketName, monyFromWhere, notes)

        }

        //recycleView beállítása
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

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    startDate = String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth)
                    binding.textView1.text = "Kezdődátum: $startDate"
                }, year, month, day
            )
            datePickerDialog.show()
        }

        binding.button2.setOnClickListener {

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    startDate = String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth)
                    binding.textView2.text = "Végső dátum: $startDate"
                }, year, month, day
            )
            datePickerDialog.show()
        }

        viewModelQuery.loadItemsInBackground()
    }


    private val onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
        }
    }


}


