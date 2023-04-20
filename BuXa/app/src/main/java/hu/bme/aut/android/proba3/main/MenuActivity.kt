package hu.bme.aut.android.proba3.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import hu.bme.aut.android.proba3.R
import hu.bme.aut.android.proba3.databinding.ActivityMenuBinding
import hu.bme.aut.android.proba3.main.expenses.ActivityPocket
import hu.bme.aut.android.proba3.main.debt.FunctionDebt

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMenuBinding = DataBindingUtil.setContentView(this, R.layout.activity_menu)
        val befektetesGomb: ImageButton =findViewById(R.id.ibBefektetes)
        val koltesekGomb: ImageButton =findViewById(R.id.train)

        befektetesGomb.setOnClickListener{
            val intent = Intent(this, FunctionDebt::class.java)
            startActivity(intent)
        }
        koltesekGomb.setOnClickListener{
            val intent = Intent(this, ActivityPocket::class.java)
            startActivity(intent)
        }

    }
}
