package hu.bme.aut.android.proba3.tartozas.detailes

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.proba3.databinding.TartozasDetailesListaelemBinding
import hu.bme.aut.android.proba3.databinding.TartozasListaBinding
import hu.bme.aut.android.proba3.tartozas.data.DebtItem


class AdapterDetails(private val listener: mainFigyeloInterface) :
    RecyclerView.Adapter<AdapterDetails.SajatViewHolder>() {

    val tartozasok = mutableListOf<DebtItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SajatViewHolder(
        TartozasDetailesListaelemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    //ez mi??
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SajatViewHolder, position: Int) {
        val p = tartozasok[position]

        holder.binding.tvWho.text = p.who
        holder.binding.tvWhom.text = "${p.whom}nek"
        holder.binding.tvDescription.text = p.description
        holder.binding.tvAmount.text = "${p.amount} Ft"

        //fuggvenybe szervezheto
        if(p.isPayed){
            holder.binding.cb.isChecked=true
            holder.binding.elem.setBackgroundColor(Color.parseColor("#228B22"))
        }
        if(!p.isPayed){
            holder.binding.cb.isChecked=false
            holder.binding.elem.setBackgroundColor(Color.parseColor("#E1585B"))
        }


    }

    override fun getItemCount(): Int = tartozasok.size

    //loadItems fuggveny hasznalja
    fun update(newPayments: MutableList<DebtItem>) {
        tartozasok.clear()
        println("Tomb elemszama: %d".format(newPayments.size))
        tartozasok.addAll(newPayments)
        notifyDataSetChanged()
    }



    interface mainFigyeloInterface {
        fun onItemChanged(item: DebtItem)
        fun onItemDelete(item: DebtItem, position: Int)
    }

    inner class SajatViewHolder(val binding: TartozasDetailesListaelemBinding) : RecyclerView.ViewHolder(binding.root)
}