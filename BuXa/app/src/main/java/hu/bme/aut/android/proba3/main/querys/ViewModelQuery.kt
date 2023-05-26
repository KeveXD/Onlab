package hu.bme.aut.android.proba3.main.querys

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import hu.bme.aut.android.proba3.main.data.ExpensItem
import hu.bme.aut.android.proba3.main.data.RepositoryExpenses
import java.text.SimpleDateFormat
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
            if ((startDate == null || !compareDates(payment.date, startDate))
                && (endDate == null || !compareDates(endDate, payment.date))
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


    fun compareDates(date1: String, date2: String): Boolean {
        val format = "%04d-%02d-%02d"
        val dateRegex = Regex("""\d{4}-\d{2}-\d{2}""")

        if (!dateRegex.matches(date1) || !dateRegex.matches(date2)) {
            return false
        }

        val parts1 = date1.split("-").map { it.toInt() }
        val parts2 = date2.split("-").map { it.toInt() }

        val year1 = parts1[0]
        val month1 = parts1[1]
        val day1 = parts1[2]

        val year2 = parts2[0]
        val month2 = parts2[1]
        val day2 = parts2[2]

        val formattedDate1 = String.format(format, year1, month1, day1)
        val formattedDate2 = String.format(format, year2, month2, day2)

        return formattedDate1 < formattedDate2
    }







    interface mainObserver{

    }

}