package hu.bme.aut.android.proba3.tartozas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hu.bme.aut.android.proba3.R
import hu.bme.aut.android.proba3.databinding.ActivityTartozasBinding
import hu.bme.aut.android.proba3.tartozas.data.DebtItem

class FunctionDebt : AppCompatActivity(), AdapterDebt.mainFigyeloInterface,
    FragmentDebt.FragmentInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityTartozasBinding = DataBindingUtil.setContentView(this, R.layout.activity_tartozas)
        val adapter: AdapterDebt=AdapterDebt(this)
        val plusz: FloatingActionButton =findViewById(R.id.plusz_gomb)

        plusz.setOnClickListener{
            FragmentDebt().show(
                supportFragmentManager,
                "PaymentFragment"
            )
        }

        //Init RecicleView
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
            //loadItemsInBackground()




    }

    override fun onItemChanged(item: DebtItem) {

    }

    override fun onItemDelete(item: DebtItem, position: Int) {

    }

    override fun newPaymentCreated(newItem: DebtItem) {

    }
}




