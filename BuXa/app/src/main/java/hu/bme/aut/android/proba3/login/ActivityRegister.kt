package hu.bme.aut.android.proba3.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.proba3.R

class ActivityRegister : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerButton: Button = findViewById(R.id.button_register)
        val progressBar: ProgressBar = findViewById(R.id.progress_bar)

        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()

        registerButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            performSignUp()
        }
    }

    private fun performSignUp() {
        val emailEditText: EditText = findViewById(R.id.edit_text_email)
        val passwordEditText: EditText = findViewById(R.id.edit_text_password)

        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sikeres regisztráció
                    Toast.makeText(baseContext, "Sikeres regisztráció", Toast.LENGTH_SHORT).show()

                    // Betöltés az adatokkal
                    val currentUser = auth.currentUser
                    val userEmail = currentUser?.email
                    val userId = currentUser?.uid

                    // Felhasználó hozzáadása a Firestore adatbázishoz
                    val user = hashMapOf(
                        "email" to userEmail
                    )
                    firestore.collection("felhasznalok")
                        .document(userId!!)
                        .set(user)
                        .addOnSuccessListener {
                            // Adatok sikeresen feltöltve a Firestore adatbázisba

                            // Navigálás a bejelentkezési oldalra
                            val intent = Intent(this, ActivityLogin::class.java)
                            startActivity(intent)
                            finish()
                        }
                        .addOnFailureListener { e ->
                            // Hiba történt az adatfeltöltés során
                            Log.e(TAG, "Hiba történt az adatfeltöltés során", e)
                        }
                } else {
                    // Sikertelen regisztráció
                    Toast.makeText(baseContext, "Hibás regisztrációs adatok", Toast.LENGTH_SHORT).show()
                }
            }
    }

    companion object {
        private const val TAG = "ActivityRegister"
    }
}
