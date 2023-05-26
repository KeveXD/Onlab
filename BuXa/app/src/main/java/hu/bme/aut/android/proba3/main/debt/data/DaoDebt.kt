package hu.bme.aut.android.proba3.main.debt.data


import androidx.room.*

@Dao
interface DaoDebt {
    @Query("SELECT * FROM debtList")
    fun getAll(): List<DebtItem>

    @Insert
    fun insert(p: DebtItem): Long

    @Update
    fun update(p: DebtItem)

    @Delete
    fun deleteItem(p: DebtItem)

    @Query("DELETE FROM debtList")
    fun deleteAll()
}
