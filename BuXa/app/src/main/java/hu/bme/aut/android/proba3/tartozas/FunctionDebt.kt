package hu.bme.aut.android.proba3.tartozas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hu.bme.aut.android.proba3.R
import hu.bme.aut.android.proba3.databinding.ActivityTartozasBinding
import hu.bme.aut.android.proba3.login.LoginViewModel
import hu.bme.aut.android.proba3.tartozas.data.DatabaseDebt
import hu.bme.aut.android.proba3.tartozas.data.DebtItem
import kotlin.concurrent.thread

lateinit var viewModel: ViewModelDebt
class FunctionDebt : AppCompatActivity(), AdapterDebt.mainFigyeloInterface,
    FragmentDebt.FragmentInterface, ContactDebt {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= ViewModelProvider(this).get(ViewModelDebt::class.java)
        val binding: ActivityTartozasBinding = DataBindingUtil.setContentView(this, R.layout.activity_tartozas)
        val adapter: AdapterDebt=AdapterDebt(this)
        val database: DatabaseDebt=DatabaseDebt.getDatabase(applicationContext)
        val plusz: FloatingActionButton =findViewById(R.id.plusz_gomb)

        //init viewModel
        binding.viewmodel=viewModel
        viewModel.adapter=adapter
        viewModel.database=database
        viewModel.context=this
        viewModel.listener=this

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
        viewModel.onItemChanged2(item)
    }

    override fun onItemDelete(item: DebtItem, position: Int) {
        viewModel.onItemDelete2(item,position)
    }

    override fun newPaymentCreated(newItem: DebtItem) {
        viewModel.newPaymentCreated2(newItem)
    }
}




