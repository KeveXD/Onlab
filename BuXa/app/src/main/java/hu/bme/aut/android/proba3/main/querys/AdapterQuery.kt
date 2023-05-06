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

        }

        override fun getItemCount(): Int = payments.size


        fun update(newPayments: List<ExpensItem>) {
            payments.clear()
            payments.addAll(newPayments)
            notifyDataSetChanged()
        }


        interface AdapterInterface {
        }

        inner class ExpensesListitemViewHolder(val binding: InpocketListitemBinding) : RecyclerView.ViewHolder(binding.root)
    }