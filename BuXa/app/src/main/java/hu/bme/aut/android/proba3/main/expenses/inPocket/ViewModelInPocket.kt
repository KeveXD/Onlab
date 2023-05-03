package hu.bme.aut.android.proba3.main.expenses.inPocket

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import hu.bme.aut.android.proba3.main.expenses.data.RepositoryExpenses
import hu.bme.aut.android.proba3.main.expenses.data.ExpensItem
import kotlin.concurrent.thread


//observabledata,livedata
class ViewModelInPocket: ViewModel() {
    lateinit var database: RepositoryExpenses
    lateinit var adapter: AdapterInPocket
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
    fun newPaymentCreated(newItem: ExpensItem) {
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
            val items = database.DatabaseExpensesFun().getAll().filter { it.pocket == pocketName }
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

    interface mainObserver{

    }

}