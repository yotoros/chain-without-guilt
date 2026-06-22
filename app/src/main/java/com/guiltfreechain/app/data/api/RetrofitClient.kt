package com.guiltfreechain.app.data.api

import com.guiltfreechain.app.util.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val api: SupabaseApi by lazy {
        // Создаем OkHttp клиент с interceptor для добавления заголовков
        val client = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("apikey", Constants.ANON_KEY)
                    .addHeader("Authorization", "Bearer ${Constants.ANON_KEY}")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Prefer", "return=representation")
                    .build()
                chain.proceed(request)
            })
            .build()

        // Создаем Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Создаем API интерфейс
        retrofit.create(SupabaseApi::class.java)
    }
}