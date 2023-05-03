package hu.bme.aut.android.proba3.main.expenses

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import hu.bme.aut.android.proba3.main.expenses.data.RepositoryExpenses
import hu.bme.aut.android.proba3.main.expenses.data.ExpensItem
import kotlin.concurrent.thread

class ViewModelPocket(application: Application) : AndroidViewModel(application) {
    private val repository = RepositoryExpenses.getDatabase(application)
    //private val pocketsList = mutableListOf<String>()
    lateinit var adapter: AdapterPocket

    fun getPocketsList(): List<String> {
        return adapter.pockets.toList()
    }


    fun addPocketToList(pocketName: String) {
        adapter.pockets.add(pocketName)
    }

    fun loadItemsInBackground() {
        thread {
            val items = repository.DatabaseExpensesFun().getAll()
            adapter.pockets.clear()
            adapter.pockets.addAll(listOutPockets(items))
        }
    }

    private fun listOutPockets(items: List<ExpensItem>): List<String> {
        val newList = mutableListOf<String>()
        for (item in items) {
            if (!newList.contains(item.pocket)) {
                newList.add(item.pocket)
            }
        }
        return newList
    }
}
