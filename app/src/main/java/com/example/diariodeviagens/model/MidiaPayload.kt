package com.example.diariodeviagens.model

// Payload para a requisição POST de midia dentro de viagem
data class MidiaPayload(
    val url: String,
    val tipo: String
)