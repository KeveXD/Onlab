package hu.bme.aut.android.proba3.main.expenses.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [ExpensItem::class], version = 1)
//repo
abstract class RepositoryExpenses : RoomDatabase() {
    abstract fun DatabaseExpensesFun(): DaoExpenses

    companion object {
        fun getDatabase(applicationContext: Context): RepositoryExpenses {
            return Room.databaseBuilder(
                applicationContext,
                RepositoryExpenses::class.java,
                "ExpensList"
            ).build();
        }
    }
}