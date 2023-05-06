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

    fun filterItems(startDate: String?, endDate: String?, pocketName: String?, moneyFromWhere: String?, notes: String?) {
        val second = mutableListOf<ExpensItem>()
        for (payment in adapter.payments) {
            if ((startDate == null || !isSecondDateAfterFirst(payment.date, startDate))
                && (endDate == null || !isSecondDateAfterFirst(endDate, payment.date))
                && (pocketName == "Összes" || payment.pocket == pocketName)
                && (moneyFromWhere == null || moneyFromWhere == "" || payment.spentFor.contains(moneyFromWhere))
                && (notes == null || notes=="" || payment.description.contains(notes))) {
                second.add(payment)

            }
            
        }
        adapter.payments.clear()
        adapter.payments.addAll(second)
        adapter.notifyDataSetChanged()
    }



    fun isSecondDateAfterFirst(firstDate: String, secondDate: String): Boolean {
        val firstDateParts = firstDate.split("-")
        val secondDateParts = secondDate.split("-")

        val year1 = firstDateParts[0].toInt()
        val month1 = firstDateParts[1].toInt()
        val day1 = firstDateParts[2].toInt()

        val year2 = secondDateParts[0].toInt()
        val month2 = secondDateParts[1].toInt()
        val day2 = secondDateParts[2].toInt()

        if (year2 > year1) {
            return true
        } else if (year2 == year1 && month2 > month1) {
            return true
        } else if (year2 == year1 && month2 == month1 && day2 > day1) {
            return true
        }

        return false
    }






    interface mainObserver{

    }

}