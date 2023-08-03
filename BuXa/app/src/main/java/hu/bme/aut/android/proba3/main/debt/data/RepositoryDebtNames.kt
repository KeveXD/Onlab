package hu.bme.aut.android.proba3.main.debt.data



import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DebtName::class], version = 1)

abstract class RepositoryDebtNames : RoomDatabase() {
    abstract fun databaseDebtFun(): DaoDebtNames

    companion object {
        fun getDatabase(applicationContext: Context): RepositoryDebtNames {
            return Room.databaseBuilder(
                applicationContext,
                RepositoryDebtNames::class.java,
                "nameList"
            ).build();
        }
    }
}