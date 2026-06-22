package com.guiltfreechain.app.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password_hash") val passwordHash: String,
    @SerializedName("created_at") val createdAt: String? = null
)