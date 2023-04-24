package hu.bme.aut.android.proba3.main.expenses.inPocket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.proba3.databinding.DebtListitemBinding
import hu.bme.aut.android.proba3.databinding.InpocketListitemBinding
import hu.bme.aut.android.proba3.main.debt.data.DebtItem
import hu.bme.aut.android.proba3.main.expenses.data.ExpensItem


class AdapterInPocket(private val listener: AdapterInterface) :
    RecyclerView.Adapter<AdapterInPocket.ExpensesListitemViewHolder>() {

    private val payments = mutableListOf<ExpensItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ExpensesListitemViewHolder(
        InpocketListitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ExpensesListitemViewHolder, position: Int) {
        val p = payments[position]

        holder.binding.tvDate.text=p.date
        holder.binding.tvFrom.text=p.spentFor
        holder.binding.tvNotes.text=p.description
        holder.binding.tvAmount.text = "${p.amount} Ft"



        //a kuka gomb megnyomasakor meghivjuk a torles fuggvenyt a helyi interfacen
        holder.binding.ibEdit.setOnClickListener(){
            listener.onItemDelete(p,position)
        }
    }

    override fun getItemCount(): Int = payments.size


    fun addItem(item: ExpensItem) {
        payments.add(item)
        notifyItemInserted(payments.size - 1)
    }

    fun update(newPayments: List<ExpensItem>) {
        payments.clear()
        payments.addAll(newPayments)
        notifyDataSetChanged()
    }

    fun delete(position: Int){
        payments.removeAt(position)
        notifyDataSetChanged()
    }

    interface AdapterInterface {
        //azert kell mert a viewModel majd kitorli az adatbazisbol
        fun onItemDelete(item: ExpensItem, position: Int)
    }

    inner class ExpensesListitemViewHolder(val binding: InpocketListitemBinding) : RecyclerView.ViewHolder(binding.root)
}