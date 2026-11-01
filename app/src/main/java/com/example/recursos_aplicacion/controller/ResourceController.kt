package com.example.recursos_aplicacion.controller

import com.example.recursos_aplicacion.model.ApiClient
import com.example.recursos_aplicacion.model.Recurso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ResourceController {
    suspend fun obtenerRecursos(): List<Recurso> {
        return withContext(Dispatchers.IO) {
            try {
                ApiClient.instance.getRecursos()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    suspend fun guardarRecurso(recurso: Recurso, isUpdate: Boolean, id: String?): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = if (isUpdate && id != null) {
                    ApiClient.instance.updateRecurso(id, recurso)
                } else {
                    ApiClient.instance.createRecurso(recurso)
                }
                response.isSuccessful
            } catch (e: Exception) {
                false
            }
        }
    }

    suspend fun eliminarRecurso(id: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = ApiClient.instance.deleteRecurso(id)
                response.isSuccessful
            } catch (e: Exception) {
                false
            }
        }
    }
}