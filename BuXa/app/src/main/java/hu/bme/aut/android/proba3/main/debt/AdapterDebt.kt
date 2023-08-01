package hu.bme.aut.android.proba3.main.debt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.proba3.databinding.DebtListitemBinding
import hu.bme.aut.android.proba3.databinding.P2Binding
import hu.bme.aut.android.proba3.main.debt.data.DebtItem


class AdapterDebt(private val listener: AdapterInterface) :
    RecyclerView.Adapter<AdapterDebt.DebtListitemViewHolder>() {

    private val payments = mutableListOf<DebtItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DebtListitemViewHolder(
        P2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: DebtListitemViewHolder, position: Int) {
        val p = payments[position]

        holder.binding.tvWho.text = p.who
        holder.binding.tvWhom.text = "${p.whom}nek"
        holder.binding.tvDescription.text = p.description
        holder.binding.tvAmount.text = "${p.amount} Ft"

        //a kuka gomb megnyomasakor meghivjuk a torles fuggvenyt a helyi interfacen
        holder.binding.ibEdit.setOnClickListener(){
            listener.onItemDelete(p,position)
        }
    }

    override fun getItemCount(): Int = payments.size


    fun addItem(item: DebtItem) {
        payments.add(item)
        notifyItemInserted(payments.size - 1)
    }

    fun update(newPayments: List<DebtItem>) {
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
        fun onItemDelete(item: DebtItem, position: Int)
    }

    inner class DebtListitemViewHolder(val binding: P2Binding) : RecyclerView.ViewHolder(binding.root)
}