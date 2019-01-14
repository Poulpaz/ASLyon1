package com.aslyon.lpiem.aslyon1.datasource.request

import com.google.gson.annotations.SerializedName
import java.util.*

class SignUpData(
        @SerializedName("lastname") var lastname: String,
        @SerializedName("firstname") var firstname: String,
        @SerializedName("dateofbirth") var dateOfBirth: String,
        @SerializedName("email") var email: String,
        @SerializedName("password") var password: String,
        @SerializedName("phonenumber") var phoneNumber: String
)