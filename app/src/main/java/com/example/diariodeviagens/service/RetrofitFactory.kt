package com.example.diariodeviagens.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {



    // Criar Retrofit uma vez só, quando a classe for instanciada
    private val retrofitFactory: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.107.134.22:8080/v1/diario-viagem/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getUserService(): UserService {
        return retrofitFactory.create(UserService::class.java)
    }

    fun getViagemService(): ViagemService {
        return retrofitFactory.create(ViagemService::class.java)
    }
    fun getCategoriaService(): CategoriaService {
        return retrofitFactory.create(CategoriaService::class.java)
    }
    fun getNominatimService(): CategoriaService {
        return retrofitFactory.create(CategoriaService::class.java)
    }
<<<<<<< HEAD








=======
>>>>>>> 35803435c3ee6db3a1568360d707f416aa97655f
}
