package hu.bme.aut.android.proba3

import android.view.View
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import hu.bme.aut.android.proba3.login.AuthListener

class AuthViewModel : ViewModel() {

    var email: String?=null
    var password: String?=null
    var auth: FirebaseAuth?=null

    var authListener: AuthListener?=null

    fun onLoginButtonClick(view: View){
        authListener?.onStarted()
        if (email.isNullOrEmpty()||password.isNullOrEmpty())
        {
            authListener?.onFailure("Nem jo a email vagy a jelszó")
            return
        }

    }

}