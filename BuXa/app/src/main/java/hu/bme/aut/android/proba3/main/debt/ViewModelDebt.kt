package hu.bme.aut.android.proba3.main.debt

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import hu.bme.aut.android.proba3.main.debt.data.RepositoryDebt
import hu.bme.aut.android.proba3.main.debt.data.DebtItem
import kotlin.concurrent.thread

class ViewModelDebt : ViewModel() {
    lateinit var database: RepositoryDebt
    lateinit var adapter: AdapterDebt
    lateinit var context: Context

    fun onItemDelete(item: DebtItem, position: Int) {
        thread {
            database.DatabaseDebtFun().deleteItem(item)

            (context as Activity).runOnUiThread {
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
}
