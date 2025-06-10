package com.example.diariodeviagens.model

data class NominatimResult(
    val display_name: String, // Usado como 'nome'
    val lat: String,          // Usado como 'latitude'
    val lon: String           // Usado como 'longitude'
)
