package hu.bme.aut.android.proba3.main.expenses.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hu.bme.aut.android.proba3.main.debt.data.DaoDebt





@Database(entities = [ExpensItem::class], version = 1)
//repo
abstract class DatabaseExpenses : RoomDatabase() {
    abstract fun DatabaseExpensesFun(): DaoExpenses

    companion object {
        fun getDatabase(applicationContext: Context): DatabaseExpenses {
            return Room.databaseBuilder(
                applicationContext,
                DatabaseExpenses::class.java,
                "ExpensList"
            ).build();
        }
    }
}