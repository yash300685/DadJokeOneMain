package com.onemain.challenge.models

data class JokesModel(

    val results: List<Result>

)

fun JokesModel.toJokesUIModel():List<JokesUIModel>
{
    return results.map {JokesUIModel(it.joke,it.id)}
}