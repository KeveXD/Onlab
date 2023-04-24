package hu.bme.aut.android.proba3.main.expenses.inPocket


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.proba3.databinding.DialogDebtBinding
import hu.bme.aut.android.proba3.databinding.DialogExpensesBinding
import hu.bme.aut.android.proba3.main.debt.data.DebtItem
import hu.bme.aut.android.proba3.main.expenses.data.ExpensItem


class FragmentExpenses : DialogFragment() {

    private lateinit var listener: FragmentInterface

    private lateinit var binding: DialogExpensesBinding

    //Activity-hez csatolja a dialógust
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? FragmentInterface
            ?: throw RuntimeException("Activity must implement the DialogInterface interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogExpensesBinding.inflate(LayoutInflater.from(context))


        return AlertDialog.Builder(requireContext()).setTitle("Új tartozás hozzáadása").setView(binding.root)
            .setPositiveButton("OK") {
                    dialogInterface, i -> listener.newPaymentCreated(getItem())
            }.setNegativeButton("Mégsem", null).create()
    }

    private fun isValid(): Boolean
    {
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

    interface FragmentInterface {
        fun newPaymentCreated(newItem: ExpensItem)
    }
}