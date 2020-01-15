package com.onemain.challenge.models

/**
 * Model class for populating json response from api
 */
data class JokesModel(

    val results: List<Result>

)

//extension function to convert to JokesUIModel which will be consumed by UI
fun JokesModel.toJokesUIModel():List<JokesUIModel>
{
    return results.map {JokesUIModel(it.joke,it.id)}
}