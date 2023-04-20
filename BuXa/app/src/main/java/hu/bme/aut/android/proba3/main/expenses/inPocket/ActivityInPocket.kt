package hu.bme.aut.android.proba3.main.expenses.inPocket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import hu.bme.aut.android.proba3.R
import hu.bme.aut.android.proba3.databinding.ActivityInpocketBinding


class ActivityInPocket : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityInpocketBinding = DataBindingUtil.setContentView(this, R.layout.activity_inpocket)

        val name = intent.getStringExtra("name2")

        binding.tvPocket.text=name.toString()
    }
}
