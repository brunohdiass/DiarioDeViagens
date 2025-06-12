package com.example.diariodeviagens.service

import com.example.diariodeviagens.model.Viagens // Este é para o GET
import com.example.diariodeviagens.model.ViagensPayload // <--- NOVO: Para o POST
import com.example.diariodeviagens.model.ViagensResponse // Este é para o GET
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


interface ViagemService {
    @Headers("Content-Type: application/json")
    @POST("viagem")
    suspend fun postarViagem(@Body viagem: ViagensPayload): Response<Void> // <--- MUDANÇA AQUI: Usa ViagensPayload

    @GET("viagem")
    suspend fun listarViagem(): ViagensResponse
}