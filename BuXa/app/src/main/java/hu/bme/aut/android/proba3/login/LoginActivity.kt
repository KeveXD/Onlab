package hu.bme.aut.android.proba3.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.proba3.R
import hu.bme.aut.android.proba3.databinding.LoginBinding
import hu.bme.aut.android.proba3.main.MenuActivity

class LoginActivity : AppCompatActivity(), LoginListener {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: LoginBinding = DataBindingUtil.setContentView(this, R.layout.login)
        val viewModel= ViewModelProvider(this).get(LoginViewModel::class.java)
        val registerText: TextView=findViewById(R.id.text_register)
        val SignInButton: Button =findViewById(R.id.button_sign_in)

        registerText.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }



        binding.viewmodel=viewModel
        viewModel.authListener=this
        auth= Firebase.auth
        viewModel.auth=auth
        viewModel.activity=this

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
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(baseContext, "Autentikáció sikeres", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Autentikációs hiba", Toast.LENGTH_SHORT).show()
                }
            }

    }

    override fun lol() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
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


