package hu.bme.aut.android.proba3.main.expens_income.data


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