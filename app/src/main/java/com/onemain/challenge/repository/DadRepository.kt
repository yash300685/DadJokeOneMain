package com.onemain.challenge.repository

import com.onemain.challenge.result.JokeResult

/**
 * Repository layer interface
 */
interface DadRepository {
    suspend fun getAllJokes(searchTerm:String):JokeResult<Any>
}