package com.example.diariodeviagens.model

data class UsuarioAPI(
    val id: Int,
    val nome: String,
    val username: String,
    val email: String,
    val senha: String,
    val biografia: String?, // Pode ser nulo
    val data_conta: String?, // Pode ser nulo
    val palavra_chave: String,
    val foto_perfil: String? // Pode ser nulo
)