package com.onemain.challenge.result

import com.onemain.challenge.enums.Status

sealed class JokeResult<Any>(val value:Any,val status: Status)
{
    data class Success(val result:Any):JokeResult<Any>(result,Status.SUCCESS)
    data class Failure(val exception:String):JokeResult<Any>(exception,Status.FAILURE)
    data class LOADING(val isLoading:Boolean):JokeResult<Any>(isLoading,Status.LOADING)
}