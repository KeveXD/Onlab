import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.proba3.databinding.DialogExpensesBinding
import hu.bme.aut.android.proba3.main.expens_income.data.ExpensItem
import java.text.SimpleDateFormat
import java.util.*

class FragmentExpenses(private val pocketName: String?) : DialogFragment(),
    DatePickerDialog.OnDateSetListener {

    private lateinit var listener: FragmentInterface
    private lateinit var binding: DialogExpensesBinding
    private val calendar: Calendar = Calendar.getInstance()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? FragmentInterface
            ?: throw RuntimeException("Activity must implement the FragmentInterface interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogExpensesBinding.inflate(LayoutInflater.from(context))

        binding.etDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(), this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        return AlertDialog.Builder(requireContext())
            .setTitle("Új tartozás hozzáadása")
            .setView(binding.root)
            .setPositiveButton("OK") { _, _ ->
                listener.addItem(getItem())
            }
            .setNegativeButton("Mégsem", null)
            .create()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        binding.etDate.setText(dateFormat.format(calendar.time))
    }

    private fun getItem(): ExpensItem {
        val pocket2: String = pocketName ?: "Béla"
        return ExpensItem(
            pocket = pocket2,
            date = binding.etDate.text.toString(),
            amount = binding.etAmount.text.toString().toIntOrNull() ?: 0,
            spentFor = binding.etFrom.text.toString(),
            description = binding.etDescription.text.toString(),
            expenseOrIncome = "expense"
        )
    }

    interface FragmentInterface {
        fun addItem(newItem: ExpensItem)
    }
}
