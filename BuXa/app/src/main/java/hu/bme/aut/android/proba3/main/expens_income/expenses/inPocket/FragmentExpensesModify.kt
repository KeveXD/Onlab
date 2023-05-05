package hu.bme.aut.android.proba3.main.expens_income.expenses.inPocket

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.proba3.databinding.DialogExpensesBinding
import hu.bme.aut.android.proba3.main.data.ExpensItem
import java.util.*


class FragmentExpensesModify(private val expensItem: ExpensItem? = null,
                             private val pocketName: String?,
                             private val callerName: String?) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var listener: FragmentInterface2? = null

    private lateinit var binding: DialogExpensesBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? FragmentInterface2
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogExpensesBinding.inflate(LayoutInflater.from(context))

        if (expensItem != null) {
            binding.etAmount.setText(expensItem.amount.toString())
            binding.etDate.setText(expensItem.date)
            binding.etFrom.setText(expensItem.spentFor)
            binding.etDescription.setText(expensItem.description)
        }

        val calendar = Calendar.getInstance()

        binding.etDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(requireContext(), this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(if (expensItem == null) "Új tartozás hozzáadása" else "Tartozás módosítása")
            .setView(binding.root)
            .setPositiveButton("Mentés") { dialogInterface, i ->
                listener?.addItem(getItem())
            }
            .setNegativeButton("Törlés", null)
            .create()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        binding.etDate.setText("$dayOfMonth/${month + 1}/$year")
    }

    private fun isValid(): Boolean {
        if (binding.etAmount.text.isNotEmpty())
            return true
        return false
    }

    private fun getItem(): ExpensItem{
        val pocket2: String = pocketName ?: "Béla"
        val caller: String = callerName ?: "expenses"
        return ExpensItem(
            pocket = pocket2,
            date = binding.etDate.text.toString(),
            amount = binding.etAmount.text.toString().toIntOrNull() ?: 0,
            spentFor=binding.etFrom.text.toString(),
            description = binding.etDescription.text.toString(),
            expenseOrIncome = caller
        )
    }

    interface FragmentInterface2 {
        fun addItem(newItem: ExpensItem)
    }
}


