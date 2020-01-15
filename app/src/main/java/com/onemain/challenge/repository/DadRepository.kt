package com.onemain.challenge.repository

import com.onemain.challenge.result.JokeResult

interface DadRepository {
    suspend fun getAllJokes(searchTerm:String):JokeResult<Any>
}