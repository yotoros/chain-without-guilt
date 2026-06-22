package com.guiltfreechain.app.data.model

import com.google.gson.annotations.SerializedName

data class Log(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("habit_id") val habitId: Int,
    @SerializedName("date") val date: String,
    @SerializedName("completed") val completed: Boolean = false
)