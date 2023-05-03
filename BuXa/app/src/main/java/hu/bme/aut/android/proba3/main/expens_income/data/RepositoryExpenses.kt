package hu.bme.aut.android.proba3.main.expens_income.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [ExpensItem::class], version = 2)
abstract class RepositoryExpenses : RoomDatabase() {
    abstract fun DatabaseExpensesFun(): DaoExpenses

    companion object {
        fun getDatabase(applicationContext: Context): RepositoryExpenses {
            return Room.databaseBuilder(
                applicationContext,
                RepositoryExpenses::class.java,
                "ExpensList"
            //Az semavaltoztatasok miatt kell ez: .fallbackToDestructiveMigration()
            ).fallbackToDestructiveMigration().build();
        }
    }
}



