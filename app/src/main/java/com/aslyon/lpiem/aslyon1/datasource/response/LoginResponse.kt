package com.aslyon.lpiem.aslyon1.datasource.response

import com.aslyon.lpiem.aslyon1.model.User
import com.google.gson.annotations.SerializedName

class LoginResponse(
        //@SerializedName("user") var user: User,
        @SerializedName("token") var token: String
)