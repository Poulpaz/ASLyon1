package com.aslyon.lpiem.aslyon1.datasource.response

import com.aslyon.lpiem.aslyon1.model.User
import com.google.gson.annotations.SerializedName
import java.util.*

class LoginResponse(
        @SerializedName("idUser") var id: Int,
        @SerializedName("lastname") var lastname: String,
        @SerializedName("firstname") var firstname: String,
        @SerializedName("dateOfBirth") var dateOfBirth: Date,
        @SerializedName("email") var email: String,
        @SerializedName("password") var password: String,
        @SerializedName("phoneNumber") var phoneNumber: String,
        @SerializedName("token") var token: String
)