package hu.bme.aut.android.proba3.login


interface AuthListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
    fun check(email: String,password: String)
}