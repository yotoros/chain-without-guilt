package com.guiltfreechain.app.data.model

data class Log(
    val id: Int,
    val habitId: Int,
    val completed: Boolean,
    val note: String,
    val date: Long
)