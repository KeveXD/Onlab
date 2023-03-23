package hu.bme.aut.android.proba3


interface AuthListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}