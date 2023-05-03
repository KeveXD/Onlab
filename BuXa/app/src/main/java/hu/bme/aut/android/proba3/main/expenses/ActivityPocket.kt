package hu.bme.aut.android.proba3.main.expenses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import hu.bme.aut.android.proba3.databinding.ActivityPocketBinding
import hu.bme.aut.android.proba3.main.expenses.inPocket.ActivityInPocket

class ActivityPocket : AppCompatActivity(), AdapterPocket.AdapterInterface {
    private lateinit var adapter: AdapterPocket
    private lateinit var viewModel: ViewModelPocket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPocketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = AdapterPocket(this)
        binding.recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(ViewModelPocket::class.java)
        viewModel.adapter=adapter

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
                viewModel.addPocketToList(pocketName)
                binding.editText.setText("")
                binding.linieEditText.visibility = View.GONE
            }
        }

        plusButton.setOnClickListener(plusButtonClickListener)
        saveButton.setOnClickListener(saveButtonClickListener)

        viewModel.loadItemsInBackground()
        adapter.update(viewModel.getPocketsList())

    }

    //adapterbol hivodik
    override fun activityCall(name: String) {
        val intent = Intent(this, ActivityInPocket::class.java).apply {
            putExtra("name2", name)
        }
        startActivity(intent)
    }



}
