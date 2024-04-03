package com.example.biometricapplication

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class FaceAuth : AppCompatActivity() {
    private lateinit var biometricPrompt: BiometricPrompt

    private val CAMERA_PERMISSION_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_auth)

        // Check for camera permission
        if (hasCameraPermission()) {
            initializeBiometricPrompt()
        } else {
            requestCameraPermission()
        }
    }

    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }

    private fun initializeBiometricPrompt() {
        // Check if face authentication is available
        val biometricManager = BiometricManager.from(this)
        if (biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS) {
            // Initialize Executor
            val executor = ContextCompat.getMainExecutor(this)

            // Create BiometricPrompt instance
            biometricPrompt = BiometricPrompt(this, executor, biometricAuthenticationCallback)

            // Create BiometricPrompt.PromptInfo
            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Face Authentication")
                .setSubtitle("Log in using your face")
                .setDescription("Place your face in front of the camera to authenticate.")
                .setNegativeButtonText("Cancel")
                .build()

            // Trigger biometric authentication
            biometricPrompt.authenticate(promptInfo)
        } else {
            // Face authentication is not available, show an error message
            Toast.makeText(
                this,
                "Face authentication is not available on this device.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, initialize BiometricPrompt
                initializeBiometricPrompt()
            } else {
                // Permission denied, show an error message
                Toast.makeText(
                    this,
                    "Camera permission is required for face authentication.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private val biometricAuthenticationCallback = object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            // Handle authentication error
            Toast.makeText(applicationContext, "Biometric authentication error: $errString", Toast.LENGTH_SHORT).show()
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            // Biometric authentication successful
            Toast.makeText(applicationContext, "Biometric authentication successful!", Toast.LENGTH_SHORT).show()
            // Proceed with app logic after biometric authentication
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            // Biometric authentication failed
            Toast.makeText(applicationContext, "Biometric authentication failed", Toast.LENGTH_SHORT).show()
        }
    }
}