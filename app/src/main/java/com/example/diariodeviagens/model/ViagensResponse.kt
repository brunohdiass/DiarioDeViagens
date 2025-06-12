package com.example.diariodeviagens.model

data class ViagensResponse(
    val status: Boolean,
    val status_code: Int,
    val item: Int,
    val viagem: List<Viagens>
)
