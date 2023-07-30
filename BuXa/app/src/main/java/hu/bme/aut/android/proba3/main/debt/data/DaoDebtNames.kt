package hu.bme.aut.android.proba3.main.debt.data


import androidx.room.*

@Dao
interface DaoDebtNames {

    @Query("SELECT * FROM nameList")
    fun getAllNames(): List<DebtName>

    @Insert
    fun insertNames(p: DebtName): Long

    @Update
    fun updateNames(p: DebtName)

    @Delete
    fun deleteItemNames(p: DebtName)

    @Query("DELETE FROM nameList")
    fun deleteAllNames()

}
