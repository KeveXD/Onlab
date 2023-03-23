package hu.bme.aut.android.proba3

import android.view.View
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {

    var email: String?=null
    var password: String?=null

    var authListener: AuthListener?=null

    fun onLoginButtonClick(view: View){
        authListener?.onStarted()
        if (email.isNullOrEmpty()||password.isNullOrEmpty()){
            authListener?.onFailure("Nem jo a email vagy a jelszó")
            return
        }

        authListener?.onSuccess()
    }

}