package hu.bme.aut.android.proba3.main.expenses


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import hu.bme.aut.android.proba3.databinding.ActivityPocketBinding
import androidx.appcompat.widget.AppCompatEditText


lateinit var adapter: AdapterPocket
class ActivityPocket : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPocketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = AdapterPocket()
        binding.recyclerView.adapter = adapter // adapter hozzáadása a RecyclerView-hoz

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
    }





}
