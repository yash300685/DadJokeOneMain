package com.onemain.challenge.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface DadJokeRetrofitService {
    @GET("/")
    suspend fun nextJoke(): DadJoke

    companion object {
        fun get(): DadJokeRetrofitService {
            val client = OkHttpClient.Builder()
                .addInterceptor {
                    it.proceed(
                        it.request().newBuilder()
                            .header("Accept", "application/json")
                            .build()
                    )
                }
                .build()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://icanhazdadjoke.com/")
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create<DadJokeRetrofitService>(DadJokeRetrofitService::class.java)
        }
    }
}