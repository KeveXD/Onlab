package hu.bme.aut.android.proba3.tartozas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hu.bme.aut.android.proba3.R
import hu.bme.aut.android.proba3.databinding.ActivityTartozasBinding
import hu.bme.aut.android.proba3.login.LoginViewModel
import hu.bme.aut.android.proba3.login.RegisterActivity
import hu.bme.aut.android.proba3.tartozas.data.DatabaseDebt
import hu.bme.aut.android.proba3.tartozas.data.DebtItem
import hu.bme.aut.android.proba3.tartozas.detailes.Detailes
import kotlin.concurrent.thread

lateinit var viewModel: ViewModelDebt
class FunctionDebt : AppCompatActivity(), AdapterDebt.mainFigyeloInterface,
    FragmentDebt.FragmentInterface, ViewModelDebt.mainFigyelo {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= ViewModelProvider(this).get(ViewModelDebt::class.java)
        val binding: ActivityTartozasBinding = DataBindingUtil.setContentView(this, R.layout.activity_tartozas)
        val adapter: AdapterDebt=AdapterDebt(this)
        val database: DatabaseDebt=DatabaseDebt.getDatabase(applicationContext)
        val plusz: FloatingActionButton =findViewById(R.id.plusz_gomb)
        val tovabb: Button = findViewById(R.id.bTovabb)

        //init viewModel
        binding.viewmodel=viewModel
        viewModel.adapter=adapter
        viewModel.database=database
        viewModel.context=this
        //viewModel.listener=this

        //megnyit egy fragmentet
        plusz.setOnClickListener{
            FragmentDebt().show(
                supportFragmentManager,
                "PaymentFragment"
            )
        }

        //tovabb megy a szamolas ablakra (details)
        tovabb.setOnClickListener{
            val intent = Intent(this, Detailes::class.java)
            startActivity(intent)
        }

        //Init RecicleView
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        viewModel.loadItemsInBackground()
    }

    //AdapterDebt interface-ben van
    override fun onItemDelete(item: DebtItem, position: Int) {
        viewModel.onItemDelete(item,position)
    }

    //FragmentDebt interface-ben van
    override fun newPaymentCreated(newItem: DebtItem) {
        viewModel.newPaymentCreated(newItem)
    }
}




