package com.aslyon.lpiem.aslyon1.datasource.request

import com.google.gson.annotations.SerializedName
import java.util.*

class OfferData (
        @SerializedName("title") var title: String,
        @SerializedName("date") var date: String?,
        @SerializedName("teams") var teams: String?,
        @SerializedName("price") var price: String?,
        @SerializedName("link") var link: String?,
        @SerializedName("description") var description: String?)


