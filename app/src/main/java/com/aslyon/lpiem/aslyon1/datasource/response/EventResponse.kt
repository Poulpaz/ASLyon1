package com.aslyon.lpiem.aslyon1.datasource.response

import com.google.gson.annotations.SerializedName
import java.util.*

class EventResponse  (
        @SerializedName("idEvent") var idEvent: Int,
        @SerializedName("title") var title: String,
        @SerializedName("date") var date: String,
        @SerializedName("place") var place: String,
        @SerializedName("price") var price: String,
        @SerializedName("description") var description: String
)