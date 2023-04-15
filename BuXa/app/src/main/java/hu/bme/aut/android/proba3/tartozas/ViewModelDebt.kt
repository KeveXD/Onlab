package hu.bme.aut.android.proba3.tartozas

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel

import hu.bme.aut.android.proba3.tartozas.data.DatabaseDebt
import hu.bme.aut.android.proba3.tartozas.data.DebtItem
import kotlin.concurrent.thread

class ViewModelDebt: ViewModel() {
    lateinit var database: DatabaseDebt
    lateinit var adapter: AdapterDebt
    //runOnUiThread miatt kell
    lateinit var context: Context


    fun onItemDelete(item: DebtItem, position: Int) {
        thread {
            database.DatabaseDebtFun().deleteItem(item)
            (context as Activity).runOnUiThread{
                adapter.delete(position)
            }
        }
    }

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