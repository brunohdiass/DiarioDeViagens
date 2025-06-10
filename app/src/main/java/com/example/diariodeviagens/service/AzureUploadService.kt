package com.example.diariodeviagens.service

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.PUT
import retrofit2.http.Url
import okhttp3.RequestBody
import retrofit2.Retrofit
import java.io.File
import java.io.FileInputStream
import java.util.UUID

// Interface para a API de upload do Azure Blob Storage
interface AzureBlobApiService {
    @PUT
    @Headers("x-ms-blob-type: BlockBlob")
    suspend fun uploadFile(
        @Url uploadUrl: String,
        @retrofit2.http.Body fileBytes: RequestBody
    ): Response<Void>
}

// Cliente Retrofit para Azure Blob Storage
object AzureBlobRetrofit {
    // A URL base não importa muito aqui, já que estamos usando @Url na interface
    // Usaremos um URL base temporário, pois o @Url sobrescreve.
    private const val BASE_URL = "https://placeholder.blob.core.windows.net/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            // REMOVA A LINHA ABAIXO OU COLOQUE UM CONVERTER VÁLIDO SE PRECISAR
            // .addConverterFactory() // Esta linha está causando o erro
            .build()
    }

    val apiService: AzureBlobApiService by lazy {
        retrofit.create(AzureBlobApiService::class.java)
    }
}

// Função para fazer upload de arquivo para Azure Blob Storage
suspend fun uploadFileToAzure(
    file: File,
    storageAccount: String,
    sasToken: String,
    containerName: String
): String? {
    val blobName = "${UUID.randomUUID()}-${file.name}" // Usar UUID para nome único
    val baseUrl = "https://${storageAccount}.blob.core.windows.net/${containerName}/${blobName}"
    val uploadUrl = "${baseUrl}?${sasToken}"

    return try {
        val fileBytes = FileInputStream(file).readBytes()
        val requestBody = fileBytes.toRequestBody(file.extension.toMediaTypeOrNull())

        val response = AzureBlobRetrofit.apiService.uploadFile(uploadUrl, requestBody)

        if (response.isSuccessful) {
            println("Upload bem-sucedido para: $baseUrl")
            baseUrl
        } else {
            val errorBody = response.errorBody()?.string()
            println("Erro no upload para Azure Blob Storage: ${response.code()} - $errorBody")
            null
        }
    } catch (e: Exception) {
        e.printStackTrace()
        println("Erro ao tentar fazer upload para Azure Blob Storage: ${e.message}")
        null
    }
}