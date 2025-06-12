package com.example.diariodeviagens.model

data class Viagens(
    val id: Int, // Adicionado
    val titulo: String,
    val descricao: String,
    val data_inicio: String,
    val data_fim: String,
    val visibilidade: String,
    val data_criacao: String?, // Pode ser nulo
    val usuario: List<UsuarioAPI>?, // Agora é uma lista de UsuarioAPI
    val locais: List<LocalAPI>?, // Agora é uma lista de LocalAPI
    val categorias: List<CategoriaAPI>?, // Agora é uma lista de CategoriaAPI
    val midias: List<Midia>? // Já era lista de Midia, mas Midia foi atualizado
)