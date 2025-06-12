package com.example.diariodeviagens.model

// Payload para a requisição POST de criar viagem
data class ViagensPayload(
    val titulo: String,
    val descricao: String,
    val data_inicio: String,
    val data_fim: String,
    val visibilidade: String,
    val id_usuario: Int, // A API espera o ID do usuário diretamente
    val categorias: List<Int>? = null, // A API espera lista de IDs
    val locais: List<Int>? = null,     // A API espera lista de IDs
    val midias: List<MidiaPayload>? = null // Usará o novo MidiaPayload
)