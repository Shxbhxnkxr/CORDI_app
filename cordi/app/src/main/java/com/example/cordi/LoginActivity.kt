package com.example.cordi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val patientButton: Button = findViewById(R.id.patient_button)
        val observerButton: Button = findViewById(R.id.observer_button)

        patientButton.setOnClickListener {
            startActivity(Intent(this, PatientLoginActivity::class.java))
        }

        observerButton.setOnClickListener {
            startActivity(Intent(this, ObserverRegistrationActivity::class.java))
        }
    }
}
