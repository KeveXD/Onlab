package hu.bme.aut.android.proba3.login

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import hu.bme.aut.android.proba3.main.MenuActivity

class LoginViewModel : ViewModel() {

    var email: String?=null
    var password: String?=null

    var auth: FirebaseAuth?=null
    lateinit var activity: Activity

    var authListener: LoginListener?=null

    fun onLoginButtonClick(view: View){
        authListener?.onStarted()
        if (email.isNullOrEmpty()||password.isNullOrEmpty())
        {
            authListener?.onFailure("Nem jo a email vagy a jelszó")
            return
        }

        auth?.signInWithEmailAndPassword(email!!, password!!)
            ?.addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    authListener?.lol()
                } else {
                    // If sign in fails, display a message to the user.
                }
            }
    }

}