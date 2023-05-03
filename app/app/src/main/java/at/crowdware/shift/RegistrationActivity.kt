package at.crowdware.shift


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        supportActionBar?.hide()
    }
}
