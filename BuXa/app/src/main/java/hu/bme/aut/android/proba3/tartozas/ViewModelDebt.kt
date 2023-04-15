package hu.bme.aut.android.proba3.tartozas

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel

import hu.bme.aut.android.proba3.tartozas.data.DatabaseDebt
import hu.bme.aut.android.proba3.tartozas.data.DebtItem
import kotlin.concurrent.thread

class ViewModelDebt: ViewModel() {
    var listener: ContactDebt?=null
    lateinit var database: DatabaseDebt
    lateinit var adapter: AdapterDebt
    //runOnUiThread miatt kell
    lateinit var context: Context

    fun loadItemsInBackground2() {
        thread {
            val items = database.DatabaseDebtFun().getAll()
            (context as Activity).runOnUiThread {
                adapter.update(items)
            }
        }
    }

    fun onItemChanged2(item: DebtItem) {
        thread {
            database.DatabaseDebtFun().update(item)
            Log.d("MainActivity", "Payment update was successful")
        }
    }

    fun onItemDelete2(item: DebtItem, position: Int) {
        thread {
            database.DatabaseDebtFun().deleteItem(item)
            (context as Activity).runOnUiThread{
                adapter.delete(position)
            }
        }
    }

    fun newPaymentCreated2(newItem: DebtItem) {
        thread {
            val insertId = database.DatabaseDebtFun().insert(newItem)
            newItem.id = insertId
            (context as Activity).runOnUiThread {
                adapter.addItem(newItem)
            }
        }
    }


}