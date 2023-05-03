package hu.bme.aut.android.proba3.main.expens_income

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.proba3.databinding.PocketListitemBinding


class AdapterPocket(private val listener: AdapterInterface) :
    RecyclerView.Adapter<AdapterPocket.ViewHolder>() {

     val pockets = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        PocketListitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p = pockets[position]
        holder.binding.myTextView.text=p.toString()

        holder.binding.myImageButton.setOnClickListener{
            listener.activityCall(holder.binding.myTextView.text.toString())
        }
    }

    override fun getItemCount(): Int = pockets.size


    fun addItem(name: String) {
        pockets.add(name)
        notifyItemInserted(pockets.size - 1)
    }

    fun update(newPockets: List<String>) {
        pockets.clear()
        pockets.addAll(newPockets)
        notifyDataSetChanged()
    }



    interface AdapterInterface {
        fun activityCall(name: String)
    }


    inner class ViewHolder(val binding: PocketListitemBinding) : RecyclerView.ViewHolder(binding.root)
}