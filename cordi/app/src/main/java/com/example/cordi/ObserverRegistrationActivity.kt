package com.example.cordi

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ObserverRegistrationActivity : AppCompatActivity() {

    private lateinit var observerId: EditText
    private lateinit var observerPassword: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_observer_registration)

        observerId = findViewById(R.id.observer_id)
        observerPassword = findViewById(R.id.observer_password)
        registerButton = findViewById(R.id.observer_register_button)

        registerButton.setOnClickListener {
            // Register logic here (you might want to save observer info to a database)
            Toast.makeText(this, "Observer Registered Successfully!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
