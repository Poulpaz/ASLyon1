package com.aslyon.lpiem.aslyon1.datasource.request

import com.google.gson.annotations.SerializedName

class EventData (

        @SerializedName("title") var title: String,
        @SerializedName("date") var date: String?,
        @SerializedName("place") var place: String?,
        @SerializedName("price") var price: String?,
        @SerializedName("description") var description: String?
)