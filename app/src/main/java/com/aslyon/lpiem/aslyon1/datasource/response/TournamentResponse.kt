package com.aslyon.lpiem.aslyon1.datasource.response

import com.google.gson.annotations.SerializedName

class TournamentResponse (
    @SerializedName("idTournament") var idTournament: Int,
    @SerializedName("title") var title : String,
    @SerializedName("nbTeam") var nbTeam : String,
    @SerializedName("nbPlayersTeam") var nbPlayersTeam : String,
    @SerializedName("date") var date : String,
    @SerializedName("place") var place : String,
    @SerializedName("description") var description : String,
    @SerializedName("price") var price :String
)
