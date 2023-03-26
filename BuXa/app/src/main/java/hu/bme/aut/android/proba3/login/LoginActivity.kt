package hu.bme.aut.android.proba3.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.proba3.AuthViewModel
import hu.bme.aut.android.proba3.R
import hu.bme.aut.android.proba3.databinding.LoginBinding
import hu.bme.aut.android.proba3.main.MenuActivity
import hu.bme.aut.android.proba3.main.PocketActivity

class LoginActivity : AppCompatActivity(), AuthListener {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: LoginBinding = DataBindingUtil.setContentView(this, R.layout.login)
        val viewModel= ViewModelProvider(this).get(AuthViewModel::class.java)
        val registerText: TextView=findViewById(R.id.text_register)

        registerText.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.viewmodel=viewModel
        viewModel.authListener=this
        auth= Firebase.auth
        viewModel.auth=auth


    }

    override fun onStarted() {
        Toast.makeText(this, "Az ellenőrzés elkezdődött", Toast.LENGTH_LONG).show()
    }

    override fun onSuccess() {
        Toast.makeText(this, "Sikeres", Toast.LENGTH_LONG).show()
    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun check(email: String,password: String) {
        auth?.createUserWithEmailAndPassword(email!!, password!!)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(baseContext, "Autentikáció sikeres", Toast.LENGTH_SHORT).show()

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Autentikációs hiba", Toast.LENGTH_SHORT).show()
                }
            }

    }
    private fun performLogin(email: String, password: String){
        auth?.createUserWithEmailAndPassword(email!!, password!!)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(baseContext, "Autentikáció sikeres", Toast.LENGTH_SHORT).show()

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Autentikációs hiba", Toast.LENGTH_SHORT).show()
                }
            }
    }

}


