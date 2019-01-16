package com.aslyon.lpiem.aslyon1.datasource.response

import com.google.gson.annotations.SerializedName

class SubscribersEventResponse(
        @SerializedName("idUser") var id: Int,
        @SerializedName("lastname") var lastname: String,
        @SerializedName("firstname") var firstname: String,
        @SerializedName("idEvent") var idEvent: Int,
        @SerializedName("title") var title: String
)