package com.example.diariodeviagens.service

import com.example.diariodeviagens.model.Localizacao
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LocalizacaoService {
    @POST("localizacoes")
    suspend fun adicionarLocalizacao(@Body localizacao: Localizacao): Response<Void>
}