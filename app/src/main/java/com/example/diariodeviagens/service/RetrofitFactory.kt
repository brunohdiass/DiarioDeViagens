package com.example.diariodeviagens.service


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactory {

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private val retrofitFactory: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.107.134.22:8080/v1/diario-viagem/")
            .client(client) // Aqui está o client com o Content-Type
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

    fun getLocalService(): LocalService {
        return retrofitFactory.create(LocalService::class.java)
    }

    object NominatimApi {
        private const val BASE_URL = "https://nominatim.openstreetmap.org/"

        fun create(): NominatimService {
            val client = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .header("User-Agent", "DiarioDeViagensApp/1.0 (devgioxavier@gmail.com)")
                        .build()
                    chain.proceed(request)
                }
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(NominatimService::class.java)
        }
    }
}