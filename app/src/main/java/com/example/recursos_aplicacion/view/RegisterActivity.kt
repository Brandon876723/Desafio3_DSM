package com.example.recursos_aplicacion.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recursos_aplicacion.R
import com.example.recursos_aplicacion.controller.AuthController

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val edtEmail = findViewById<EditText>(R.id.edtRegEmail)
        val edtPassword = findViewById<EditText>(R.id.edtRegPassword)
        val spinnerRole = findViewById<Spinner>(R.id.spinnerRole)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString()
            val role = spinnerRole.selectedItem.toString() // "Estudiante" o "Docente"

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validar regla estricta de contraseña
            if (!AuthController.validatePassword(password)) {
                Toast.makeText(
                    this,
                    "La contraseña debe tener mín. 12 caracteres, 1 mayúscula, 1 minúscula, 1 número y 1 carácter especial (!@#$%^&*)",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            // Guardar usuario localmente (SharedPreferences)
            val prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            prefs.edit().putString("email", email).putString("role", role).apply()

            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
            finish() // Volver al login
        }
    }
}