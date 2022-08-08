package com.dalakoti07.android.core.data.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login")
    val name: String,
    @SerializedName("avatar_url")
    val avatar: String,
)

