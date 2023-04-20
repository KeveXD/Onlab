package hu.bme.aut.android.proba3.main.expenses


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import hu.bme.aut.android.proba3.databinding.ActivityPocketBinding
import androidx.appcompat.widget.AppCompatEditText
import hu.bme.aut.android.proba3.main.expenses.inPocket.ActivityInPocket


lateinit var adapter: AdapterPocket
class ActivityPocket : AppCompatActivity(), AdapterPocket.AdapterInterface {
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPocketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = AdapterPocket(this)
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

    override fun activityCall(name: String) {
        val intent = Intent(this, ActivityInPocket::class.java).apply {
            putExtra("name2", name)
        }
        startActivity(intent)
    }


}
