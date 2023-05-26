package hu.bme.aut.android.proba3.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hu.bme.aut.android.proba3.R
import hu.bme.aut.android.proba3.ViewModelHolder
import hu.bme.aut.android.proba3.databinding.ActivityMenuBinding
import hu.bme.aut.android.proba3.main.expens_income.ActivityPocket
import hu.bme.aut.android.proba3.main.debt.FunctionDebt
import hu.bme.aut.android.proba3.main.debt.data.DebtItem
import hu.bme.aut.android.proba3.main.debt.data.RepositoryDebt
import hu.bme.aut.android.proba3.main.expens_income.ActivityQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext




class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMenuBinding = DataBindingUtil.setContentView(this, R.layout.activity_menu)
        val debtButton: ImageButton =findViewById(R.id.ibDebt)
        val expensesButton: ImageButton =findViewById(R.id.ibExpens)
        val incomeButton: ImageButton =findViewById(R.id.ibIncome)
        val queryButton: ImageButton =findViewById(R.id.ibQuery)
        val saveButton: ImageButton =findViewById(R.id.ibSave)
        val loadButton: ImageButton =findViewById(R.id.ibLoad)


        debtButton.setOnClickListener{
            val intent = Intent(this, FunctionDebt::class.java)
            startActivity(intent)
        }
        queryButton.setOnClickListener{
            val intent = Intent(this, ActivityQuery::class.java)
            startActivity(intent)
        }
        expensesButton.setOnClickListener{
            val intent = Intent()

            // Az ActivityPocket osztály nevének átadása
            intent.putExtra("caller", "expenses")
            intent.setClass(this, ActivityPocket::class.java)
            startActivity(intent)
        }
        incomeButton.setOnClickListener{
            val intent = Intent()

            // Az ActivityPocket osztály nevének átadása
            intent.putExtra("caller", "income")
            intent.setClass(this, ActivityPocket::class.java)
            startActivity(intent)
        }


        val firestore = FirebaseFirestore.getInstance()
        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
        fun showToast(message: String) {
            runOnUiThread {
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            }
        }





        saveButton.setOnClickListener {
            ViewModelHolder.viewModelLogin.email?.let { email ->
                println("Email: $email") // Kiírja az e-mail címet a konzolra

                val usersCollection = firestore.collection("felhasznalok")
                val query = usersCollection.whereEqualTo("email", email)

                query.get().addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        val userDocument = querySnapshot.documents[0]
                        val dataCollection = userDocument.reference.collection("adatok")

                        // Adatok törlése a kollekcióból
                        dataCollection.get().addOnSuccessListener { snapshot ->
                            val batch = firestore.batch()
                            for (document in snapshot.documents) {
                                batch.delete(document.reference)
                            }
                            batch.commit()
                                .addOnSuccessListener {
                                    // Lekérdezés az összes elemről a Room adatbázisból
                                    CoroutineScope(Dispatchers.IO).launch {
                                        val database = RepositoryDebt.getDatabase(applicationContext)
                                        val debtItems = database.DatabaseDebtFun().getAll()

                                        // Mentés az adatok kollekcióban
                                        val saveBatch = firestore.batch()
                                        for (item in debtItems) {
                                            saveBatch.set(dataCollection.document(), item)
                                        }
                                        saveBatch.commit()
                                            .addOnSuccessListener {
                                                showToast("Adatok sikeresen mentve")
                                            }
                                            .addOnFailureListener { exception ->
                                                showToast("Adatok mentése sikertelen: ${exception.message}")
                                            }
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    showToast("Adatok törlése sikertelen: ${exception.message}")
                                }
                        }.addOnFailureListener { exception ->
                            showToast("Hiba történt a dokumentum lekérésekor: ${exception.message}")
                        }
                    } else {
                        showToast("Felhasználói dokumentum nem található")
                    }
                }.addOnFailureListener { exception ->
                    showToast("Hiba történt a dokumentum lekérésekor: ${exception.message}")
                }
            }
        }




        loadButton.setOnClickListener {
            ViewModelHolder.viewModelLogin.email?.let { email ->
                val usersCollection = firestore.collection("felhasznalok")
                val userDocument = usersCollection.document(ViewModelHolder.viewModelLogin.email.toString())

                userDocument.get().addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val dataCollection = documentSnapshot.reference.collection("adatok")

                        // Törölje az összes elemet a Room adatbázisból
                        CoroutineScope(Dispatchers.IO).launch {
                            val database = RepositoryDebt.getDatabase(applicationContext)
                            val daoDebt = database.DatabaseDebtFun()
                            daoDebt.deleteAll()

                            // Betöltés az adatokból a Room adatbázisba
                            val newDebtItems = dataCollection.get().await().toObjects(DebtItem::class.java)
                            for (newItem in newDebtItems) {
                                daoDebt.insert(newItem)
                            }
                            showToast("Adatok sikeresen betöltve")
                        }
                    } else {
                        showToast("Felhasználói dokumentum nem található")
                    }
                }.addOnFailureListener { exception ->
                    showToast("Hiba történt a dokumentum lekérésekor: ${exception.message}")
                }
            }
        }
























    }
}
