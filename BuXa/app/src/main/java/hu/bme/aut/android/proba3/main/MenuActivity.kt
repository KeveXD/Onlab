package hu.bme.aut.android.proba3.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import hu.bme.aut.android.proba3.R
import hu.bme.aut.android.proba3.databinding.ActivityMenuBinding
import hu.bme.aut.android.proba3.login.RegisterActivity
import hu.bme.aut.android.proba3.tartozas.Tartozas

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMenuBinding = DataBindingUtil.setContentView(this, R.layout.activity_menu)
        val befektetesGomb: ImageButton =findViewById(R.id.ibBefektetes)

        befektetesGomb.setOnClickListener{
            val intent = Intent(this, Tartozas::class.java)
            startActivity(intent)
        }

    }
}
