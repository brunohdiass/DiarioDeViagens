package com.example.diariodeviagens.service



import com.example.diariodeviagens.model.CategoriaResponse
import retrofit2.Response
import retrofit2.http.GET

interface CategoriaService {
    @GET("categoria")
    suspend fun getCategorias(): Response<CategoriaResponse> // Agora espera o wrapper completo
}