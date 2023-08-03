package hu.bme.aut.android.proba3

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import hu.bme.aut.android.proba3.databinding.P1Binding
import hu.bme.aut.android.proba3.main.debt.AdapterDebt
import hu.bme.aut.android.proba3.main.debt.data.RepositoryDebt
import hu.bme.aut.android.proba3.main.debt.data.RepositoryDebtNames
import hu.bme.aut.android.proba3.main.debt.data.RepositoryDebtNames_Impl
import hu.bme.aut.android.proba3.main.expens_income.expenses.inPocket.binding

class NameActivity: AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: P1Binding = DataBindingUtil.setContentView(this, R.layout.name_activity)
        //val adapter = AdapterDebt(this)
        val database: RepositoryDebtNames = RepositoryDebtNames.getDatabase(applicationContext)

        val addName: ImageButton = binding.bAddName

    }
}