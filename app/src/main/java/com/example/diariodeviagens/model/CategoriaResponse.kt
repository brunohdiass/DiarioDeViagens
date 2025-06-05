package com.example.diariodeviagens.model

import com.google.gson.annotations.SerializedName

data class CategoriaResponse(


    @SerializedName("status") val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("item") val itemCount: Int,
    @SerializedName("categoria") val categorias: List<Categoria>
)


data class Categoria(
    @SerializedName("id") val id: Int,
    @SerializedName("nome_categoria") val nome: String
)

