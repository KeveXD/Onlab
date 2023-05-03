package hu.bme.aut.android.proba3.main.debt.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DebtItem::class], version = 1)

abstract class RepositoryDebt : RoomDatabase() {
    abstract fun DatabaseDebtFun(): DaoDebt

    companion object {
        fun getDatabase(applicationContext: Context): RepositoryDebt {
            return Room.databaseBuilder(
                applicationContext,
                RepositoryDebt::class.java,
                "DedtList"
            ).build();
        }
    }
}