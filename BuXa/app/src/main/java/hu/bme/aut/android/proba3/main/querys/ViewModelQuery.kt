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

    //runOnUiThread miatt kell
    lateinit var context: Context


    fun loadItemsInBackground() {
        thread {
            val items = database.DatabaseExpensesFun().getAll()
            (context as Activity).runOnUiThread {
                adapter.update(items)
            }
        }
    }

    fun getPockets(callback: (List<String>) -> Unit) {
        thread {
            val pockets = mutableListOf<String>()
            val items = database.DatabaseExpensesFun().getAll()
            for (item in items) {
                if (!pockets.contains(item.pocket)) {
                    pockets.add(item.pocket)
                }
            }
            pockets.add(0, "Összes")
            (context as Activity).runOnUiThread {
                callback(pockets)
            }
        }
    }




    interface mainObserver{

    }

}