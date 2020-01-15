package com.onemain.challenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.onemain.challenge.repository.DadRepository
import com.onemain.challenge.result.JokeResult
import kotlinx.coroutines.Dispatchers


class MainViewModel(private val repository: DadRepository) : ViewModel() {

    private var jokesData = MutableLiveData<JokeResult<Any>>()

 init {
     jokesData= liveData(Dispatchers.IO) {
         emit(repository.getAllJokes(""))
     } as MutableLiveData<JokeResult<Any>>
 }

    fun getAllJokes(): LiveData<JokeResult<Any>> {     // Simple getter
        return jokesData
    }

    fun refreshJokes() : LiveData<JokeResult<Any>> = liveData(Dispatchers.IO) {
        emit(JokeResult.LOADING(true))
        emit(repository.getAllJokes(""))
    }

    fun searchJoke(searchTerm: String): LiveData<JokeResult<Any>> = liveData(Dispatchers.IO) {
        emit(JokeResult.LOADING(true))
        emit(repository.getAllJokes(searchTerm))
    }


}
