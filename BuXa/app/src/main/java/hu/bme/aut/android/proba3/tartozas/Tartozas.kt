package hu.bme.aut.android.proba3.tartozas

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hu.bme.aut.android.proba3.R
import hu.bme.aut.android.proba3.databinding.ActivityTartozasBinding
import hu.bme.aut.android.proba3.databinding.LoginBinding
import hu.bme.aut.android.proba3.login.RegisterActivity

class Tartozas : AppCompatActivity(), PaymentAdapter.mainFigyeloInterface,
    PaymentFragment.FragmentInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityTartozasBinding = DataBindingUtil.setContentView(this, R.layout.activity_tartozas)
        val plusz: FloatingActionButton =findViewById(R.id.plusz_gomb)

        plusz.setOnClickListener{
            PaymentFragment().show(
                supportFragmentManager,
                "PaymentFragment"
            )
        }




    }

    override fun onItemChanged(item: DebtDatabase) {

    }

    override fun onItemDelete(item: DebtDatabase, position: Int) {
        TODO("Not yet implemented")
    }

    override fun newPaymentCreated(newItem: DebtDatabase) {
        TODO("Not yet implemented")
    }
}




