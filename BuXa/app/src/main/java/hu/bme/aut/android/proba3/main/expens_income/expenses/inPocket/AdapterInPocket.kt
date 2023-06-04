package hu.bme.aut.android.proba3.main.expens_income.expenses.inPocket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.proba3.databinding.InpocketListitemBinding
import hu.bme.aut.android.proba3.main.data.ExpensItem


class AdapterInPocket(private val pocketName: String?, private val listener: AdapterInterface) :
    RecyclerView.Adapter<AdapterInPocket.ExpensesListitemViewHolder>() {

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


        //a kuka gomb megnyomasakor meghivjuk a torles fuggvenyt a helyi interfacen
        holder.binding.ibEdit.setOnClickListener(){
            listener.openFragmentModify(payments[position])
            listener.onItemDelete(p,position)
        }
    }

    override fun getItemCount(): Int = payments.size

    fun calculateSpentMoney(): Int{
        var totalTvAmount = 0
        for(i in payments)
        {
            if (i.expenseOrIncome=="income")
            totalTvAmount+=i.amount
        }
        return totalTvAmount
    }
    fun calculateMoney(): Int{
        var totalTvAmount = 0
        for(i in payments)
        {
            if (i.expenseOrIncome=="expenses")
                totalTvAmount+=i.amount
        }
        return totalTvAmount
    }


    fun addItem(item: ExpensItem) {

        payments.add(item)
        notifyItemInserted(payments.size - 1)
        listener.setSpentMoney(calculateSpentMoney())
        listener.setSumMoney(calculateMoney())
    }

    fun update(newPayments: List<ExpensItem>) {
        payments.clear()
        payments.addAll(newPayments)
        notifyDataSetChanged()
        listener.setSpentMoney(calculateSpentMoney())
        listener.setSumMoney(calculateMoney())
    }

    fun delete(position: Int){
        payments.removeAt(position)
        notifyDataSetChanged()
        listener.setSpentMoney(calculateSpentMoney())
        listener.setSumMoney(calculateMoney())
    }

    interface AdapterInterface {
        //azert kell mert a viewModel majd kitorli az adatbazisbol
        fun onItemDelete(item: ExpensItem, position: Int)
        fun setSpentMoney(p: Int)
        fun setSumMoney(p: Int)
        fun openFragmentModify(item: ExpensItem)
    }

    inner class ExpensesListitemViewHolder(val binding: InpocketListitemBinding) : RecyclerView.ViewHolder(binding.root)
}