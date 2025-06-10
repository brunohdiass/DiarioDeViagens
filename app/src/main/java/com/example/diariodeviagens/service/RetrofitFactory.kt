package com.example.diariodeviagens.service


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactory {


    // Criar Retrofit uma vez só, quando a classe for instanciada
    private val retrofitFactory: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.107.144.24:8080/v1/diario-viagem/")
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

    object NominatimApi {
        private const val BASE_URL = "https://nominatim.openstreetmap.org/"

        fun create(): NominatimService {
            // Crie um OkHttpClient e adicione um interceptor para o User-Agent
            val client = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS) // Aumenta o tempo limite de conexão
                .readTimeout(30, TimeUnit.SECONDS)    // Aumenta o tempo limite de leitura
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        // **MUITO IMPORTANTE:** Substitua "SeuAplicativoDeViagens/1.0 (seuemail@example.com)"
                        // pelo nome do seu aplicativo e um contato de e-mail.
                        // Isso é essencial para o Nominatim.
                        .header("User-Agent", "DiarioDeViagensApp/1.0 (devgioxavier@gmail.com)")
                        .build()
                    chain.proceed(request)
                }
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client) // Adiciona o cliente OkHttpClient com o interceptor
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(NominatimService::class.java)
        }
    }
}




