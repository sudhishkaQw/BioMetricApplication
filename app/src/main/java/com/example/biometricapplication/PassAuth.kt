package com.example.biometricapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class PassAuth : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pass_auth)
        val loginButton: Button = findViewById(R.id.loginButton)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
        loginButton.setOnClickListener {
            val enteredPassword = passwordEditText.text.toString()
            if (authenticateWithPassword(enteredPassword)) {
                // Password authentication successful, proceed with app logic
                Toast.makeText(this, "Password authentication successful!", Toast.LENGTH_SHORT).show()
            } else {
                // Password authentication failed, trigger biometric authentication
                Toast.makeText(this, "Password authentication Failed!", Toast.LENGTH_SHORT).show()

            }
        }
    }
    private fun authenticateWithPassword(password: String): Boolean {
        // Replace this with your password validation logic
        val storedPassword = "HelloWorld"
        return password == storedPassword
    }
    }

