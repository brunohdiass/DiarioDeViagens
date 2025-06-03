package com.example.diariodeviagens.service

import com.example.diariodeviagens.model.Viagens
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface ViagemService {
    @Headers("Content-Type: application/json")
    @POST("viagem")
    suspend fun postarViagem(@Body viagem: Viagens): Response<Void>

}