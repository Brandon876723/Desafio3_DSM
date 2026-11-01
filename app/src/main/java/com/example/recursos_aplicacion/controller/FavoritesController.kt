package com.example.recursos_aplicacion.controller

import android.content.Context

class FavoritesController(context: Context) {
    private val prefs = context.getSharedPreferences("FavoritosPrefs", Context.MODE_PRIVATE)

    fun marcarFavorito(idRecurso: String, esFavorito: Boolean) {
        prefs.edit().putBoolean(idRecurso, esFavorito).apply()
    }

    fun esFavorito(idRecurso: String): Boolean {
        return prefs.getBoolean(idRecurso, false)
    }
}