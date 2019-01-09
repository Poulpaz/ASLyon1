package com.aslyon.lpiem.aslyon1.model

import com.google.gson.annotations.SerializedName
import java.util.*

class Tournament(
        @SerializedName("id") var idTournament: Int,
        @SerializedName("title") var title: String,
        @SerializedName("nbTeam") var nbTeam: Int,
        @SerializedName("nbPlayersTeam") var nbPlayersTeam: Int,
        @SerializedName("date") var date: Date,
        @SerializedName("description") var description: String,
        @SerializedName("price") var price: Double
)