package hu.bme.aut.android.proba3.main.expens_income

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import hu.bme.aut.android.proba3.R
import hu.bme.aut.android.proba3.databinding.ActivityPocketBinding
import hu.bme.aut.android.proba3.main.expens_income.AdapterPocket.Companion.SELECT_IMAGE_REQUEST_CODE
import hu.bme.aut.android.proba3.main.expens_income.expenses.inPocket.ActivityInPocket

class ActivityPocket : AppCompatActivity(), AdapterPocket.AdapterInterface {
    private lateinit var adapter: AdapterPocket
    private lateinit var viewModel: ViewModelPocket
    private var callerName: String? = null
    private lateinit var selectedImageButton: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPocketBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //init adapter
        adapter = AdapterPocket(this)
        binding.recyclerView.adapter = adapter

        //init viewModel
        viewModel = ViewModelProvider(this).get(ViewModelPocket::class.java)
        viewModel.adapter=adapter

        //init callerName
        callerName = intent.getStringExtra("caller")


        val plusButton: Button =binding.button
        val saveButton: Button =binding.bSave
        binding.linieEditText.visibility = View.GONE

        val plusButtonClickListener = View.OnClickListener {
            if (binding.linieEditText.visibility == View.VISIBLE) {
                binding.linieEditText.visibility = View.GONE
            } else {
                binding.linieEditText.visibility = View.VISIBLE
            }
        }

        val saveButtonClickListener = View.OnClickListener {
            val pocketName = binding.editText.text.toString()

            if (pocketName.isNotEmpty()) {
                viewModel.addPocketToList(pocketName)
                binding.editText.setText("")
                binding.linieEditText.visibility = View.GONE
            }
        }

        plusButton.setOnClickListener(plusButtonClickListener)
        saveButton.setOnClickListener(saveButtonClickListener)

        viewModel.loadItemsInBackground()
        adapter.update(viewModel.getPocketsList())

        if (callerName=="income")
            binding.tvTitle.text="Income"
        if (callerName=="expenses")
            binding.tvTitle.text="Expenses"


    }

    //adapterbol hivodik
    override fun activityCall(name: String) {
        if (callerName.isNullOrEmpty()){
            return
        }
            val intent = Intent()
            intent.putExtra("caller", callerName)
            intent.putExtra("name2", name)
            intent.setClass(this, ActivityInPocket::class.java)
            startActivity(intent)

    }

    override fun activityCall2(ib: ImageButton) {
        selectedImageButton=ib
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        ActivityCompat.startActivityForResult(this, intent, SELECT_IMAGE_REQUEST_CODE, null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELECT_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val imageUri: Uri = data.data ?: return
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            selectedImageButton.setImageBitmap(bitmap)
        }
    }



}
