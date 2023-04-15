package hu.bme.aut.android.proba3.tartozas.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DebtItem::class], version = 1)

abstract class DatabaseDebt : RoomDatabase() {
    abstract fun DatabaseDebtFun(): DaoDebt

    companion object {
        fun getDatabase(applicationContext: Context): DatabaseDebt {
            return Room.databaseBuilder(
                applicationContext,
                DatabaseDebt::class.java,
                "DedtList"
            ).build();
        }
    }
}