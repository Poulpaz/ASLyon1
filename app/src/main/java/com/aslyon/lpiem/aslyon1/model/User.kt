package com.aslyon.lpiem.aslyon1.model

import com.google.gson.annotations.SerializedName
import java.util.*

class User(
        @SerializedName("idUser") var id: Int,
        @SerializedName("lastname") var lastname: String,
        @SerializedName("firstname") var firstname: String,
        @SerializedName("dateOfBirth") var dateOfBirth: Date,
        @SerializedName("email") var email: String,
        @SerializedName("password") var password: String,
        @SerializedName("phoneNumber") var phoneNumber: String
)