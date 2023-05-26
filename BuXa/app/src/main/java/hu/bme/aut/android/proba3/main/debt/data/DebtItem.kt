package hu.bme.aut.android.proba3.main.debt.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "debtList")
data class DebtItem(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "who") var who: String,
    @ColumnInfo(name = "whom") var whom: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "amount") var amount: Int,
    @ColumnInfo(name = "is_payed") var isPayed: Boolean
) {
    constructor() : this(null, "", "", "", 0, false)
}
