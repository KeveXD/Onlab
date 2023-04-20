package hu.bme.aut.android.proba3.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.proba3.R
import hu.bme.aut.android.proba3.databinding.ActivityLoginBinding
import hu.bme.aut.android.proba3.main.MenuActivity

class ActivityLogin : AppCompatActivity(), LoginListener {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel= ViewModelProvider(this).get(ViewModelLogin::class.java)
        val registerText: TextView=findViewById(R.id.text_register)
        val SignInButton: Button =findViewById(R.id.button_sign_in)

        registerText.setOnClickListener{
            val intent = Intent(this, ActivityRegister::class.java)
            startActivity(intent)
        }



        binding.viewmodel=viewModel
        viewModel.listener=this
        auth= Firebase.auth
        viewModel.auth=auth
        viewModel.activity=this

        binding.viewmodel = viewModel.apply {
            email = "arpi@gmail.com"
            password = "Arpi123"
        }

        viewModel.listener = this
        auth = Firebase.auth
        viewModel.auth = auth
        viewModel.activity = this

        binding.editTextEmail.addTextChangedListener {
            viewModel.email = it.toString()
        }

        binding.editTextPassword.addTextChangedListener {
            viewModel.password = it.toString()
        }


        binding.editTextEmail.addTextChangedListener {
            viewModel.email = it.toString()
        }

        binding.editTextPassword.addTextChangedListener {
            viewModel.password = it.toString()
        }

    }

    //UI elemekkel valo kommunikalas
    override fun toast(szoveg: String) {
        Toast.makeText(this, szoveg, Toast.LENGTH_LONG).show()
    }


    override fun check(email: String,password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(baseContext, "Autentikáció sikeres csekkolás", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Autentikációs hiba csekkolásnál", Toast.LENGTH_SHORT).show()
                }
            }

    }

    override fun callMenu() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }

}


