package hu.bme.aut.android.proba3.main.data



import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "ExpensList")
data class ExpensItem(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "pocket") var pocket: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "amount") var amount: Int,
    @ColumnInfo(name = "spentFor") var spentFor: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "Expense/Income") var expenseOrIncome: String
)
