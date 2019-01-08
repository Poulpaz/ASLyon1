package com.aslyon.lpiem.aslyon1.datasource.request

import com.google.gson.annotations.SerializedName

data class LoginData constructor(
        @SerializedName("email") val mail: String,
        @SerializedName("password") val password: String
)