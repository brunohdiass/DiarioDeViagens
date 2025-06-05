package com.example.diariodeviagens.service
import com.example.diariodeviagens.model.Endereço
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit



    interface NominatimApiService {
        @GET("search?format=json&addressdetails=1&limit=1")
        suspend fun searchAddress(
            @Query("q") query: String,
            @Query("accept-language") lang: String = "pt-BR"
        ): List<NominatimResponseItem>
    }

    data class NominatimResponseItem(
        val lat: String,
        val lon: String,
        @SerializedName("display_name")
        val displayName: String,
        val address: AddressDetails?
    )

    data class AddressDetails(
        val postcode: String?
    )

    object NominatimRetrofit {
        private const val BASE_URL = "https://nominatim.openstreetmap.org/"

        private val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("User-Agent", "DiarioDeViagensApp/1.0")
                    .build()
                chain.proceed(request)
            }
            .build()

        val apiService: NominatimApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NominatimApiService::class.java)
        }
    }

    fun extrairCEP(displayName: String): String? {
        val regex = "\\b\\d{5}-?\\d{3}\\b".toRegex() // Handles both 12345-678 and 12345678 formats
        return regex.find(displayName)?.value
    }

    suspend fun buscarCoordenadasComEndereco(enderecoString: String): Endereço? {
        return try {
            val result = NominatimRetrofit.apiService.searchAddress(enderecoString)
            result.firstOrNull()?.let { item ->
                Endereço(
                    cep = item.address?.postcode ?: extrairCEP(item.displayName) ?: "",
                    displayName = item.displayName,
                    lat = item.lat.toDoubleOrNull() ?: 0.0,
                    lon = item.lon.toDoubleOrNull() ?: 0.0
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


