package com.example.cordi

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PatientRegistrationActivity : AppCompatActivity() {

    private lateinit var patientId: EditText
    private lateinit var patientPassword: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_registration)

        patientId = findViewById(R.id.patient_id)
        patientPassword = findViewById(R.id.patient_password)
        registerButton = findViewById(R.id.patient_register_button)

        registerButton.setOnClickListener {
            // Register logic here (you might want to save patient info to a database)
            Toast.makeText(this, "Patient Registered Successfully!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
