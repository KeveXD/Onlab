package hu.bme.aut.android.proba3.tartozas.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class DatabaseDebt : RoomDatabase() {
    abstract fun PaymentDao(): DaoDebt

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