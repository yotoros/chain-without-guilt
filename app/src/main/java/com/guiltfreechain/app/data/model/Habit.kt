package com.guiltfreechain.app.data.model

import com.google.gson.annotations.SerializedName

data class Habit(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("frequency") val frequency: String,
    @SerializedName("note") val note: String = "",
    @SerializedName("color") val color: String = "#5FB3A3",
    @SerializedName("current_rating") val currentRating: Float = 100f,
    @SerializedName("created_at") val createdAt: String? = null
)