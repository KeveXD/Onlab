package hu.bme.aut.android.proba3.main.expenses.data

import hu.bme.aut.android.proba3.main.debt.data.DebtItem


import androidx.room.*

@Dao
interface DaoExpenses {
    @Query("SELECT * FROM ExpensList")
    fun getAll(): List<ExpensItem>

    @Insert
    fun insert(p: ExpensItem): Long

    @Update
    fun update(p: ExpensItem)

    @Delete
    fun deleteItem(p: ExpensItem)
}