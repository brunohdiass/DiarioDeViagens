package com.example.diariodeviagens.model

data class Endereço(


    val cep: String,
    val displayName: String,
    val lat: Double,
    val lon: Double,
    val logradouro: String? = null,
    val complemento: String? = null,
    val bairro: String? = null,
    val localidade: String? = null,
    val uf: String? = null,
    val ibge: String? = null,
    val gia: String? = null,
    val siafi: String? = null
)


