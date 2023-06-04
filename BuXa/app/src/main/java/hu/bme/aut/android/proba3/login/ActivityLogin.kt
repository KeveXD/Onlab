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
import hu.bme.aut.android.proba3.ViewModelHolder
import hu.bme.aut.android.proba3.databinding.ActivityLoginBinding
import hu.bme.aut.android.proba3.main.MenuActivity

class ActivityLogin : AppCompatActivity(), LoginListener {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Az activity_login.xml fájlhoz kötjük a layoutot
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        // Létrehozzuk a ViewModelLogin objektumot
        val viewModelLogin = ViewModelProvider(this).get(ViewModelLogin::class.java)

        // A ViewModelLogin objektumot elmentjük a ViewModelHolder segítségével
        ViewModelHolder.viewModelLogin = viewModelLogin

        // TextView és Button elemek inicializálása
        val registerText: TextView = findViewById(R.id.text_register)
        val signInButton: Button = findViewById(R.id.button_sign_in)

        // A regisztráció szövegére kattintás eseménykezelője
        registerText.setOnClickListener{
            // Átirányítás az ActivityRegister aktivitásra
            val intent = Intent(this, ActivityRegister::class.java)
            startActivity(intent)
        }

        // A ViewModelLogin objektum beállítása a binding objektumban
        binding.viewmodel = viewModelLogin

        // A ViewModelLogin objektum beállítása a LoginListener interfésszel
        viewModelLogin.listener = this

        // Firebase hitelesítés inicializálása
        auth = Firebase.auth
        viewModelLogin.auth = auth
        viewModelLogin.activity = this

        // Az email és jelszó mezők alapértelmezett értékeinek beállítása a ViewModelLogin objektumban
        binding.viewmodel = viewModelLogin.apply {
            email = "proba@gmail.com"
            password = "Proba123"
        }

        // Az email mező figyelése és értékének beállítása a ViewModelLogin objektumban
        binding.editTextEmail.addTextChangedListener {
            viewModelLogin.email = it.toString()
        }

        // A jelszó mező figyelése és értékének beállítása a ViewModelLogin objektumban
        binding.editTextPassword.addTextChangedListener {
            viewModelLogin.password = it.toString()
        }

    }

    // Toast üzenet megjelenítése
    override fun toast(szoveg: String) {
        Toast.makeText(this, szoveg, Toast.LENGTH_LONG).show()
    }

    // Felhasználó bejelentkezésének ellenőrzése
    override fun check(email: String, password: String) {
        // Firebase hitelesítés: bejelentkezés az email és jelszó segítségével
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Ha sikeres volt a bejelentkezés, átirányítás a MenuActivity aktivitásra
                    Toast.makeText(baseContext, "Autentikáció sikeres belépés", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                } else {
                    // Ha sikertelen volt a bejelentkezés, hibaüzenet megjelenítése
                    Toast.makeText(baseContext, "Autentikációs hiba belépésnél", Toast.LENGTH_SHORT).show()
                }
            }

    }

    // Átirányítás a MenuActivity aktivitásra
    override fun callMenu() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }
    }