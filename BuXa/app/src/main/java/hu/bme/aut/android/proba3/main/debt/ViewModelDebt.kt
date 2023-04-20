package hu.bme.aut.android.proba3.main.debt

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel

import hu.bme.aut.android.proba3.main.debt.data.DatabaseDebt
import hu.bme.aut.android.proba3.main.debt.data.DebtItem
import kotlin.concurrent.thread

class ViewModelDebt: ViewModel() {
    lateinit var database: DatabaseDebt
    lateinit var adapter: AdapterDebt
    //runOnUiThread miatt kell
    lateinit var context: Context


    //kitorli az adatbazisbol a megfelelo elemet
    fun onItemDelete(item: DebtItem, position: Int) {
        thread {
            database.DatabaseDebtFun().deleteItem(item)
            (context as Activity).runOnUiThread{
                adapter.delete(position)
            }
        }
    }

    //adatbazisba menti az uj DebtItem-et
    fun newPaymentCreated(newItem: DebtItem) {
        thread {
            val insertId = database.DatabaseDebtFun().insert(newItem)
            newItem.id = insertId
            (context as Activity).runOnUiThread {
                adapter.addItem(newItem)
            }
        }
    }

    fun loadItemsInBackground() {
        thread {
            val items = database.DatabaseDebtFun().getAll()
            (context as Activity).runOnUiThread {
                adapter.update(items)
            }
        }
    }

    interface mainFigyelo{

    }

}