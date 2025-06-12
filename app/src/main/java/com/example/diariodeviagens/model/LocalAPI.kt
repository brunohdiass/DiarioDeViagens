package com.example.diariodeviagens.model

data class LocalAPI(
    val id: Int,
    val nome: String,
    val latitude: String,
    val longitude: String,
    val pais: String?, // Pode ser nulo
    val estado: String?, // Pode ser nulo
    val cidade: String? // Pode ser nulo
)