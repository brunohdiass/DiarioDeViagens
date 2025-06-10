package com.example.diariodeviagens.model

data class Viagens(
    val titulo: String,
    val descricao: String,
    val data_inicio: String,
    val data_fim: String,
    val visibilidade: String,
    val id_usuario: Int,
    val categorias: List<Int>? = null,
    val locais: List<Int>? = null, // <- envia só os IDs
    val midias: List<Midia>? = null
)

