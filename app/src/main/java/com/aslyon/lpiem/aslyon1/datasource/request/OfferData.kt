package com.aslyon.lpiem.aslyon1.datasource.request

import com.google.gson.annotations.SerializedName
import java.util.*

class OfferData (
        @SerializedName("title") var title: String,
        @SerializedName("startDate") var startDate: String?,
        @SerializedName("endDate") var endDate: String?,
        @SerializedName("nbParticipants") var nbParticipants: String?,
        @SerializedName("price") var price: String?,
        @SerializedName("description") var description: String?)


