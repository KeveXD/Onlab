package hu.bme.aut.android.proba3.tartozas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.proba3.databinding.TartozasListaBinding


class PaymentAdapter(private val listener: mainFigyeloInterface) :
    RecyclerView.Adapter<PaymentAdapter.SajatViewHolder>() {

    private val payments = mutableListOf<DebtDatabase>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SajatViewHolder(
        TartozasListaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: SajatViewHolder, position: Int) {
        val p = payments[position]

        holder.binding.tvWho.text = p.who
        holder.binding.tvWhom.text = "${p.whom}nek"
        holder.binding.tvDescription.text = p.description
        holder.binding.tvAmount.text = "${p.amount} Ft"


        holder.binding.ibEdit.setOnClickListener(){

            listener.onItemDelete(p,position)
        }
    }

    override fun getItemCount(): Int = payments.size


    fun addItem(item: DebtDatabase) {
        payments.add(item)
        notifyItemInserted(payments.size - 1)
    }

    fun update(newPayments: List<DebtDatabase>) {
        payments.clear()
        payments.addAll(newPayments)
        notifyDataSetChanged()
    }

    fun delete(position: Int){
        payments.removeAt(position)
        notifyDataSetChanged()
    }

    interface mainFigyeloInterface {
        fun onItemChanged(item: DebtDatabase)
        fun onItemDelete(item: DebtDatabase, position: Int)
    }

    inner class SajatViewHolder(val binding: TartozasListaBinding) : RecyclerView.ViewHolder(binding.root)
}