package com.example.diariodeviagens.service

import com.example.diariodeviagens.model.Categoria
import retrofit2.Response
import retrofit2.http.GET

interface CategoriaService {
    @GET("categoria") // ou a rota correta conforme exposta pela sua API
    suspend fun listarCategoria(): Response<List<Categoria>>
}