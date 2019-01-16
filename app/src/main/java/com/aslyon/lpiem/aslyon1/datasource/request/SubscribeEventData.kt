package com.aslyon.lpiem.aslyon1.datasource.request

import com.google.gson.annotations.SerializedName

class SubscribeEventData (
        @SerializedName("idUser") var idUser: Int,
        @SerializedName("idEvent") var idEvent: Int
)