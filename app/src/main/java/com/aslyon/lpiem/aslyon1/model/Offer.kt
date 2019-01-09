package com.aslyon.lpiem.aslyon1.model

import com.google.gson.annotations.SerializedName
import java.util.*

class Offer (
        @SerializedName("id") var idOffer: Int,
        @SerializedName("title") var title: String,
        @SerializedName("date") var date: Date?,
        @SerializedName("teams") var teams: String?,
        @SerializedName("price") var price: String?
)


