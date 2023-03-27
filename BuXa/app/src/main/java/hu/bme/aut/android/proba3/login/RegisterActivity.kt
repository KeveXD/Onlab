package hu.bme.aut.android.proba3.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.proba3.main.MenuActivity
import hu.bme.aut.android.proba3.R

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerButton: Button =findViewById(R.id.button_register)
        val keszButton: Button =findViewById(R.id.button)
        val progressBar: ProgressBar=findViewById(R.id.progress_bar)

        auth= Firebase.auth
        registerButton.setOnClickListener{
            progressBar.setVisibility(View.VISIBLE)
            performSignUp()
            progressBar.setVisibility(View.GONE)
        }
        keszButton.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
    private fun performSignUp(){
        val email=findViewById<EditText>(R.id.edit_text_email)
        val password=findViewById<EditText>(R.id.edit_text_password)

        val inputEmail=email.text.toString()
        val inputPassword=password.text.toString()

        auth?.createUserWithEmailAndPassword(inputEmail, inputPassword)
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
            ?.addOnFailureListener{
                Toast.makeText(this, "Errroooor: ${it.localizedMessage}", Toast.LENGTH_SHORT)
            }
    }
}