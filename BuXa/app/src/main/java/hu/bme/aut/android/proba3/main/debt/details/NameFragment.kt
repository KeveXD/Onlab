package hu.bme.aut.android.proba3.main.debt.details

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.proba3.databinding.DialogDebtBinding
import hu.bme.aut.android.proba3.main.debt.data.DebtItem


class NameFragment : DialogFragment() {

    private lateinit var listener: NameFragmentInterface

    private lateinit var binding: DialogDebtBinding

    //Activity-hez csatolja a dialógust
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NameFragmentInterface
            ?: throw RuntimeException("Activity must implement the DialogInterface interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogDebtBinding.inflate(LayoutInflater.from(context))


        return AlertDialog.Builder(requireContext()).setTitle("Új tartozás hozzáadása").setView(binding.root)
            .setPositiveButton("OK") {
                    dialogInterface, i -> listener.newPaymentCreated(getItem())
            }.setNegativeButton("Mégsem", null).create()
    }

    private fun isValid(): Boolean
    {
        if (binding.etWho.text.isNotEmpty() &&
            binding.etWhom.text.isNotEmpty() &&
            binding.etAmount.text.isNotEmpty())
            return true
        return false
    }

    private fun getItem() = DebtItem(
        who = binding.etWho.text.toString(),
        whom = binding.etWhom.text.toString(),
        description = binding.etDescription.text.toString(),
        amount = binding.etAmount.text.toString().toIntOrNull() ?: 0,
        isPayed = false

    )

    interface NameFragmentInterface {
        fun newPaymentCreated(newItem: DebtItem)
    }
}