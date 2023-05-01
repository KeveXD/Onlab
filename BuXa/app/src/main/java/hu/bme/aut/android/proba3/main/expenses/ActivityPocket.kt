package hu.bme.aut.android.proba3.main.expenses


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import hu.bme.aut.android.proba3.databinding.ActivityPocketBinding
import androidx.appcompat.widget.AppCompatEditText
import hu.bme.aut.android.proba3.main.expenses.data.DatabaseExpenses
import hu.bme.aut.android.proba3.main.expenses.data.ExpensItem
import hu.bme.aut.android.proba3.main.expenses.inPocket.ActivityInPocket
import hu.bme.aut.android.proba3.main.expenses.inPocket.AdapterInPocket
import kotlin.concurrent.thread


lateinit var adapter: AdapterPocket
class ActivityPocket : AppCompatActivity(), AdapterPocket.AdapterInterface {
        private lateinit var adapter: AdapterPocket
        private lateinit var database: DatabaseExpenses

        override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
            //viewBinding
        val binding = ActivityPocketBinding.inflate(layoutInflater)
        setContentView(binding.root)
            database = DatabaseExpenses.getDatabase(applicationContext)

            adapter = AdapterPocket(this)
            // adapter hozzáadása a RecyclerView-hoz
        binding.recyclerView.adapter = adapter

        val plusButton: Button =binding.button
        val saveButton: Button =binding.bSave
        binding.linieEditText.visibility = View.GONE

        val plusButtonClickListener = View.OnClickListener {
            if (binding.linieEditText.visibility == View.VISIBLE) {
                binding.linieEditText.visibility = View.GONE
            } else {
                binding.linieEditText.visibility = View.VISIBLE
            }
        }

        val saveButtonClickListener = View.OnClickListener {
            val pocketName = binding.editText.text.toString()

            if (pocketName.isNotEmpty()) {

                adapter.addItem(pocketName)
                binding.editText.setText("")
                binding.linieEditText.visibility = View.GONE

            }
        }

        plusButton.setOnClickListener(plusButtonClickListener)
        saveButton.setOnClickListener(saveButtonClickListener)
            loadItemsInBackground()
    }

    //adapterbol hivodik
    override fun activityCall(name: String) {
        val intent = Intent(this, ActivityInPocket::class.java).apply {
            putExtra("name2", name)
        }
        startActivity(intent)
    }

    fun loadItemsInBackground() {
        thread {
            val items = database.DatabaseExpensesFun().getAll()
            val newItems = listOutPockets(items)
            this.runOnUiThread {
                adapter.update(newItems)
            }
        }
    }

    fun listOutPockets(items: List<ExpensItem>): List<String> {
        val newList = mutableListOf<String>()
        for (item in items) {
            if (!newList.contains(item.pocket)) {
                newList.add(item.pocket)
            }
        }
        return newList
    }





}
