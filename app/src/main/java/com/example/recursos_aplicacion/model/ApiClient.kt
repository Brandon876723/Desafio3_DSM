package com.example.recursos_aplicacion.model

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {
    @GET("recursos")
    suspend fun getRecursos(): List<Recurso>

    @POST("recursos")
    suspend fun createRecurso(@Body recurso: Recurso): Response<Recurso>

    @PUT("recursos/{id}")
    suspend fun updateRecurso(@Path("id") id: String, @Body recurso: Recurso): Response<Recurso>

    @DELETE("recursos/{id}")
    suspend fun deleteRecurso(@Path("id") id: String): Response<Void>
}

object ApiClient {
    private const val BASE_URL = "https://6ae67d4f877d34ccafc2d58a.mockapi.io/:endpoint"

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}