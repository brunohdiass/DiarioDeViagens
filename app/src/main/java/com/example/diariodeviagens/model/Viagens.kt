package com.example.diariodeviagens.model

data class Viagens(
    val titulo: String,
    val descricao: String,
    val data_inicio: String,
    val data_fim: String,
    val visibilidade: String,
    val id_usuario: Int,
    val categorias: List<Int>? = null,   // lista de IDs das categorias
    val nome: String

)

