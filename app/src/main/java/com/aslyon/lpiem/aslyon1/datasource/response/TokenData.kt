package com.aslyon.lpiem.aslyon1.datasource.response

import com.google.gson.annotations.SerializedName

data class TokenData (
        @SerializedName("token")
        val oldToken: String?,
        @SerializedName("newtoken")
        val newToken: String
)