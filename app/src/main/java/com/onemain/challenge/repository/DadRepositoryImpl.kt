package com.onemain.challenge.repository

import com.onemain.challenge.data.DadJokeRetrofitService
import com.onemain.challenge.data.ResponseHandler
import com.onemain.challenge.models.toJokesUIModel
import com.onemain.challenge.result.JokeResult

/**
 * Repository layer implementation which fetches data from the api using Retrofit layer as a dependency
 */
class DadRepositoryImpl(private val networkApi:DadJokeRetrofitService, private val responseHandler: ResponseHandler) :DadRepository{

    override suspend fun getAllJokes(searchTerm:String): JokeResult<Any> {
        return try {

            responseHandler.handleSuccess(networkApi.getAllJokes(searchTerm).toJokesUIModel())

        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}