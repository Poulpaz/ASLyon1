package com.aslyon.lpiem.aslyon1.model

import com.google.gson.annotations.SerializedName
import java.util.*

class Tournament(
        @SerializedName("idTournament") var idTournament: Int,
        @SerializedName("title") var title: String,
        @SerializedName("nbTeam") var nbTeam: String,
        @SerializedName("nbPlayersTeam") var nbPlayersTeam: String,
        @SerializedName("date") var date: Date,
        @SerializedName("place") var place: String,
        @SerializedName("price") var price: String,
        @SerializedName("description") var description: String
)