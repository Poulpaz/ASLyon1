package com.aslyon.lpiem.aslyon1.datasource.response

import com.google.gson.annotations.SerializedName

class OfferResponse (
        @SerializedName("idOffer") var idOffer: Int,
        @SerializedName("title") var title: String,
        @SerializedName("startDate") var startDate: String,
        @SerializedName("endDate") var endDate: String,
        @SerializedName("nbParticipants") var nbParticipants: String,
        @SerializedName("price") var price: String,
        @SerializedName("description") var description: String)