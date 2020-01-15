package com.onemain.challenge.data

import android.content.Context
import com.onemain.challenge.R
import com.onemain.challenge.result.JokeResult
import retrofit2.HttpException

class ResponseHandler(context:Context) {
    private val context=context
    fun  handleSuccess(data: Any): JokeResult<Any> {
        return JokeResult.Success(data)
    }

    fun handleException(e: Exception): JokeResult<Any> {
        return when (e) {
            is HttpException -> JokeResult.Failure(getErrorMessage(e.code()))

            else ->JokeResult.Failure(getErrorMessage(Int.MAX_VALUE))
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            401 -> context.getString(R.string.unauthorized)
            404 -> context.getString(R.string.not_found)
            else -> context.getString(R.string.generic_error)
        }
    }
}