package com.example.biometricapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Pinauth : AppCompatActivity() {
    private lateinit var pinDigit1: EditText
    private lateinit var pinDigit2: EditText
    private lateinit var pinDigit3: EditText
    private lateinit var pinDigit4: EditText
    private lateinit var loginButton: Button
    private val correctPIN = "1234"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pinauth)
        pinDigit1 = findViewById(R.id.pinDigit1)
        pinDigit2 = findViewById(R.id.pinDigit2)
        pinDigit3 = findViewById(R.id.pinDigit3)
        pinDigit4 = findViewById(R.id.pinDigit4)
        loginButton = findViewById(R.id.loginButton)


        pinDigit1.addTextChangedListener(PinTextWatcher(pinDigit1, pinDigit2))
        pinDigit2.addTextChangedListener(PinTextWatcher(pinDigit2, pinDigit3))
        pinDigit3.addTextChangedListener(PinTextWatcher(pinDigit3, pinDigit4))
        pinDigit4.addTextChangedListener(PinTextWatcher(pinDigit4, null))
        loginButton.setOnClickListener {
            val enteredPIN = "${pinDigit1.text}${pinDigit2.text}${pinDigit3.text}${pinDigit4.text}"
            if (enteredPIN == correctPIN) {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Incorrect PIN", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private inner class PinTextWatcher(private val currentView: EditText, private val nextView: EditText?) :
        TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s?.length == 1) {
                nextView?.requestFocus()
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }
    }
