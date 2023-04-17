package hu.bme.aut.android.proba3.login

import android.app.Activity
import android.view.View
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ViewModelLogin : ViewModel() {

    var email: String?=null
    var password: String?=null

    var auth: FirebaseAuth?=null
    lateinit var activity: Activity

    var listener: LoginListener?=null

    fun onLoginButtonClick(view: View){
        listener?.toast("Az ellenőrzés elkezdődött")
        if (email.isNullOrEmpty()||password.isNullOrEmpty())
        {
            listener?.toast("Nem jo a email vagy a jelszó")
            return
        }

        auth?.signInWithEmailAndPassword(email!!, password!!)
            ?.addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {

                    listener?.check(email!!, password!!)
                } else {

                }
            }
    }

}