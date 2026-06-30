package com.example.fittracker.data.remote.wger

import com.example.fittracker.data.remote.wger.WgerApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object WgerClient {

    private const val BASE_URL = "https://wger.de/api/v2/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .callTimeout(30, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    val api: WgerApiService by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)   // 🔥 IMPORTANT FIX
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WgerApiService::class.java)
    }
}

