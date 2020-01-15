package com.onemain.challenge.data

import com.onemain.challenge.models.JokesModel
import retrofit2.http.GET
import retrofit2.http.Query

interface DadJokeRetrofitService {

    @GET("/search")
    suspend fun getAllJokes(@Query("term") searchTerm:String): JokesModel


}