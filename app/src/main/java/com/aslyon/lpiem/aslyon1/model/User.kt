package com.aslyon.lpiem.aslyon1.model

import com.google.gson.annotations.SerializedName
import java.util.*

class User(
        @SerializedName("id") var id: Int?,
        @SerializedName("lastname") var lastname: String,
        @SerializedName("firstname") var firstname: String,
        @SerializedName("dateOfBirth") var dateOfBirth: Date?,
        @SerializedName("email") var email: String?,
        @SerializedName("password") var password: Int?,
        @SerializedName("phoneNumber") var phoneNumber: String?,
        @SerializedName("isAdmin") var isAdmin: Boolean?
)