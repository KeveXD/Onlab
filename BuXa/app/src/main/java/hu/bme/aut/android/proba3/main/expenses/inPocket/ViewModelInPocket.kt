package hu.bme.aut.android.proba3.main.expenses.inPocket

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import hu.bme.aut.android.proba3.main.debt.AdapterDebt
import hu.bme.aut.android.proba3.main.debt.data.DatabaseDebt
import hu.bme.aut.android.proba3.main.debt.data.DebtItem
import hu.bme.aut.android.proba3.main.expenses.data.DatabaseExpenses
import hu.bme.aut.android.proba3.main.expenses.data.ExpensItem
import kotlin.concurrent.thread



class ViewModelInPocket: ViewModel() {
    lateinit var database: DatabaseExpenses
    lateinit var adapter: AdapterInPocket
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
            val items = database.DatabaseExpensesFun().getAll()
            (context as Activity).runOnUiThread {
                adapter.update(items)
            }
        }
    }

    interface mainFigyelo{

    }

}