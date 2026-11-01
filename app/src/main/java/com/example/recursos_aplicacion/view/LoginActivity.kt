package com.example.recursos_aplicacion.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recursos_aplicacion.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val edtEmail = findViewById<EditText>(R.id.edtLoginEmail)
        val edtPassword = findViewById<EditText>(R.id.edtLoginPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnGoToRegister = findViewById<Button>(R.id.btnGoToRegister)

        btnLogin.setOnClickListener {
            val prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val savedEmail = prefs.getString("email", "")
            val role = prefs.getString("role", "Estudiante")

            val emailInput = edtEmail.text.toString().trim()

            if (emailInput.isEmpty()) {
                Toast.makeText(this, "Ingresa tu correo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validación simple simulada con el rol guardado
            if (role == "Docente") {
                startActivity(Intent(this, TeacherMainActivity::class.java))
            } else {
                startActivity(Intent(this, StudentMainActivity::class.java))
            }
            finish()
        }

        btnGoToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}