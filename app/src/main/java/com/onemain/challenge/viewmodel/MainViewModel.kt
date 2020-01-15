package com.onemain.challenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.onemain.challenge.data.DadJoke
import com.onemain.challenge.data.DadJokeRetrofitService
import java.io.IOException

class MainViewModel : ViewModel() {
    private val retrofit = DadJokeRetrofitService.get()

    fun getJoke() = liveData<DadJoke> {
        val joke = fetchJoke()
        if (joke != null) {
            emit(joke)
        }
    }.map { dadJoke -> dadJoke.text }

    private suspend fun fetchJoke(): DadJoke? {
        return try {
            DadJokeRetrofitService.get().nextJoke()
        } catch (e: IOException) {
            null
        }
    }
}
