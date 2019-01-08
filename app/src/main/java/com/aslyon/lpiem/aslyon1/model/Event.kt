package com.aslyon.lpiem.aslyon1.model

import com.google.gson.annotations.SerializedName
import java.util.*

class Event (
        @SerializedName("id") var idEvent: Int,
        @SerializedName("title") var title: String,
        @SerializedName("date") var date: Date?,
        @SerializedName("place") var place: String?,
        @SerializedName("price") var price: String?,
        @SerializedName("description") var description: String?
)