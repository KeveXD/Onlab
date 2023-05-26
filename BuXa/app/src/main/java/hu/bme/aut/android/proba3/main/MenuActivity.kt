package hu.bme.aut.android.proba3.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import hu.bme.aut.android.proba3.R
import hu.bme.aut.android.proba3.databinding.ActivityMenuBinding
import hu.bme.aut.android.proba3.main.expens_income.ActivityPocket
import hu.bme.aut.android.proba3.main.debt.FunctionDebt
import hu.bme.aut.android.proba3.main.expens_income.ActivityQuery

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMenuBinding = DataBindingUtil.setContentView(this, R.layout.activity_menu)
        val debtButton: ImageButton =findViewById(R.id.ibDebt)
        val expensesButton: ImageButton =findViewById(R.id.ibExpens)
        val incomeButton: ImageButton =findViewById(R.id.ibIncome)
        val queryButton: ImageButton =findViewById(R.id.ibQuery)
        val saveButton: ImageButton =findViewById(R.id.ibInvest)


        debtButton.setOnClickListener{
            val intent = Intent(this, FunctionDebt::class.java)
            startActivity(intent)
        }
        queryButton.setOnClickListener{
            val intent = Intent(this, ActivityQuery::class.java)
            startActivity(intent)
        }
        expensesButton.setOnClickListener{
            val intent = Intent()

            // Az ActivityPocket osztály nevének átadása
            intent.putExtra("caller", "expenses")
            intent.setClass(this, ActivityPocket::class.java)
            startActivity(intent)
        }
        incomeButton.setOnClickListener{
            val intent = Intent()

            // Az ActivityPocket osztály nevének átadása
            intent.putExtra("caller", "income")
            intent.setClass(this, ActivityPocket::class.java)
            startActivity(intent)
        }

    }
}
