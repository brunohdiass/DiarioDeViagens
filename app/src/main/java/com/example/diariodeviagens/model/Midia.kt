package com.example.diariodeviagens.model

data class Midia(
    val id: Int?, // Adicionado
    val tipo: String,
    val url: String,
    val id_viagem: Int? // Adicionado, pode ser nulo se não vier sempre
)