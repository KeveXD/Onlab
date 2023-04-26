package hu.bme.aut.android.proba3.main.expenses.inPocket

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.proba3.databinding.DialogExpensesBinding
import hu.bme.aut.android.proba3.main.expenses.data.ExpensItem


class FragmentExpensesModify(private val expensItem: ExpensItem? = null) : DialogFragment() {

    private lateinit var listener: FragmentInterface2

    private lateinit var binding: DialogExpensesBinding

    //Activity-hez csatolja a dialógust
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? FragmentInterface2
            ?: throw RuntimeException("Activity must implement the DialogInterface interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogExpensesBinding.inflate(LayoutInflater.from(context))

        if (expensItem != null) {
            binding.etAmount.setText(expensItem.amount.toString())
            binding.etDate.setText(expensItem.date)
            binding.etFrom.setText(expensItem.spentFor)
            binding.etDescription.setText(expensItem.description)
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(if (expensItem == null) "Új tartozás hozzáadása" else "Tartozás módosítása")
            .setView(binding.root)
            .setPositiveButton("Mentés") { dialogInterface, i ->
                listener.newPaymentCreated(getItem())
            }
            .setNegativeButton("Törlés", null)
            .create()
    }

    private fun isValid(): Boolean {
        if (binding.etAmount.text.isNotEmpty())
            return true
        return false
    }

    private fun getItem() = ExpensItem(
        pocket="Lajos",
        date = binding.etDate.text.toString(),
        amount = binding.etAmount.text.toString().toIntOrNull() ?: 0,
        spentFor=binding.etFrom.text.toString(),
        description = binding.etDescription.text.toString()
    )

    interface FragmentInterface2 {
        fun newPaymentCreated(newItem: ExpensItem)
    }
}

