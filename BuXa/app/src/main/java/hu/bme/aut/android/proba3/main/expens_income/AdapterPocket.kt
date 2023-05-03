package hu.bme.aut.android.proba3.main.expens_income

import android.content.Intent
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.app.ActivityCompat.startActivityForResult
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
        holder.binding.root.setOnLongClickListener(holder)
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
        fun activityCall2(ib: ImageButton)
    }

    companion object {
        const val SELECT_IMAGE_REQUEST_CODE = 1
    }


    inner class ViewHolder(val binding: PocketListitemBinding) : RecyclerView.ViewHolder(binding.root), View.OnLongClickListener {
        init {
            binding.root.setOnLongClickListener(this)
        }

        override fun onLongClick(v: View?): Boolean {
            listener.activityCall2(binding.myImageButton)
            return true
        }

    }

}
