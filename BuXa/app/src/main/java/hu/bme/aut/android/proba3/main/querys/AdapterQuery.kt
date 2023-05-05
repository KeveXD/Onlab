package hu.bme.aut.android.proba3.main.querys


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.proba3.databinding.InpocketListitemBinding
import hu.bme.aut.android.proba3.main.data.ExpensItem


class AdapterQuery( private val listener: AdapterInterface) :
    RecyclerView.Adapter<AdapterQuery.ExpensesListitemViewHolder>() {

    val payments = mutableListOf<ExpensItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ExpensesListitemViewHolder(
        InpocketListitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ExpensesListitemViewHolder, position: Int) {
        val p = payments[position]

        holder.binding.tvDate.text=p.date
        holder.binding.tvFrom.text=p.spentFor
        holder.binding.tvNotes.text=p.expenseOrIncome
        holder.binding.tvAmount.text = "${p.amount} Ft"

        //modositas
        holder.binding.ibEdit.setOnClickListener(){
            listener.openFragmentModify(payments[position])
            listener.onItemDelete(p,position)
        }
    }

    override fun getItemCount(): Int = payments.size

    fun calculateSum(): Int{
        var totalTvAmount = 0
        for(i in payments)
        {
            totalTvAmount+=i.amount
        }
        return totalTvAmount
    }


    fun addItem(item: ExpensItem) {

        payments.add(item)
        notifyItemInserted(payments.size - 1)
        listener.setSum(calculateSum())
    }

    fun update(newPayments: List<ExpensItem>) {
        payments.clear()
        payments.addAll(newPayments)
        notifyDataSetChanged()
        listener.setSum(calculateSum())
    }

    fun delete(position: Int){
        payments.removeAt(position)
        notifyDataSetChanged()
        listener.setSum(calculateSum())
    }

    interface AdapterInterface {

        fun onItemDelete(item: ExpensItem, position: Int)
        fun setSum(p: Int)
        fun openFragmentModify(item: ExpensItem)
    }

    inner class ExpensesListitemViewHolder(val binding: InpocketListitemBinding) : RecyclerView.ViewHolder(binding.root)
}