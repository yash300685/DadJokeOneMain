package com.onemain.challenge.koinmodules

import com.onemain.challenge.data.DadJokeRetrofitService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Koin module which contains retrofit  dependency for calling the api .
 */
val networkModule = module {
    single {addInterceptor()}
    single { creteRetrofit(get()) }
    single { createNetworkApi(get()) }
}

fun creteRetrofit(client:OkHttpClient): Retrofit
{

    return Retrofit.Builder()
        .baseUrl("https://icanhazdadjoke.com")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun addInterceptor():OkHttpClient
{
    return OkHttpClient.Builder()
        .addInterceptor {
            it.proceed(
                it.request().newBuilder()
                    .header("Accept", "application/json")
                    .header("User-Agent","DadJokesApp")
                    .build()
            )
        }
        .build()
}

fun createNetworkApi(retrofit: Retrofit): DadJokeRetrofitService {
    return retrofit.create(DadJokeRetrofitService::class.java)
}