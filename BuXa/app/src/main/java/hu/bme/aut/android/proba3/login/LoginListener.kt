package hu.bme.aut.android.proba3.login


interface LoginListener {
    fun toast(szoveg: String)

    fun check(email: String,password: String)
    fun callMenu()
}