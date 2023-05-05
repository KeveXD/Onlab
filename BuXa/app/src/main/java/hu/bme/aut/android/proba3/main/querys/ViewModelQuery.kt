package hu.bme.aut.android.proba3.main.querys

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import hu.bme.aut.android.proba3.main.data.ExpensItem
import hu.bme.aut.android.proba3.main.data.RepositoryExpenses
import kotlin.concurrent.thread


class ViewModelQuery: ViewModel() {
    lateinit var database: RepositoryExpenses
    lateinit var adapter: AdapterQuery
    var pocketName: String?=null
    //runOnUiThread miatt kell
    lateinit var context: Context


    //kitorli az adatbazisbol a megfelelo elemet
    fun onItemDelete(item: ExpensItem, position: Int) {
        thread {
            database.DatabaseExpensesFun().deleteItem(item)
            (context as Activity).runOnUiThread{
                adapter.delete(position)
            }
        }
    }

    //adatbazisba menti az uj DebtItem-et
    fun addItem(newItem: ExpensItem) {
        thread {
            val insertId = database.DatabaseExpensesFun().insert(newItem)
            newItem.id = insertId
            (context as Activity).runOnUiThread {
                adapter.addItem(newItem)
            }
        }
    }

    fun loadItemsInBackground() {
        thread {
            val items = database.DatabaseExpensesFun().getAll()
            (context as Activity).runOnUiThread {
                adapter.update(items)
            }
        }
    }


    fun calculateSum(): Int{
        var totalTvAmount = 0
        for(i in adapter.payments)
        {
            totalTvAmount+=i.amount
        }

        return totalTvAmount
    }

    fun sortPaymentsByDate() {
        adapter.payments.sortBy { payment -> payment.date }
        adapter.notifyDataSetChanged()
    }


    interface mainObserver{

    }

}