package com.example.diariodeviagens.service

import com.example.diariodeviagens.model.LocalPayload
import com.example.diariodeviagens.model.LocalResponse
import com.example.diariodeviagens.model.LocalResponseWrapper
import retrofit2.http.Body
import retrofit2.http.POST

interface LocalService {
    @POST("local")
    suspend fun criarLocal(@Body local: LocalPayload): LocalResponseWrapper

}