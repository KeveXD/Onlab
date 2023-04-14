package hu.bme.aut.android.proba3.tartozas

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "debtList")
data class DebtDatabase(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "who") var who: String,
    @ColumnInfo(name = "whom") var whom: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "amount") var amount: Int,
    @ColumnInfo(name = "is_payed") var isPayed: Boolean
)
