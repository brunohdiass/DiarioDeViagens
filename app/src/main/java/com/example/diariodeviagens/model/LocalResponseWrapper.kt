package com.example.diariodeviagens.model

data class LocalResponseWrapper(
    val status: Boolean,  // Mude de Int para Boolean
    val message: String,
    val local: LocalResponse
)
