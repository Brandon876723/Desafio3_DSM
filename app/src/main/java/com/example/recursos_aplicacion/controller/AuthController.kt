package com.example.recursos_aplicacion.controller

object AuthController {
    // Mínimo 12 caracteres, 1 mayúscula, 1 minúscula, 1 número, 1 carácter especial
    fun validatePassword(password: String): Boolean {
        //para la contraseñas
        val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{12,}$"
        return password.matches(passwordRegex.toRegex())
    }
}