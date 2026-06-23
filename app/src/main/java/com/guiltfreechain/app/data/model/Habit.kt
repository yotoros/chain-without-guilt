package com.guiltfreechain.app.data.model

data class Habit(
    val id: Int,
    val userId: Int,
    val title: String,
    val frequency: String,
    val note: String,
    var currentRating: Float
)