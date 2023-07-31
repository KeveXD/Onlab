import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.android.proba3.R

class  Proba: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.p1)

        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.p1, null)

    }
}
