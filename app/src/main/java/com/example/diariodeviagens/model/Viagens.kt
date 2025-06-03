package com.example.diariodeviagens.model

data class Viagens(
    val titulo: String,
    val descricao: String,
    val data_inicio: String,
    val data_fim: String,
    val visibilidade: String = "publica",
    val id_usuario: Int,
    val locais: List<Int>,
    val categorias: List<Int>
)
