package com.aslyon.lpiem.aslyon1.datasource.request

import com.google.gson.annotations.SerializedName

class TournamentData(

@SerializedName("title") var title : String,
@SerializedName("nbteam") var nbTeam : String,
@SerializedName("nbplayersteam") var nbPlayersTeam : String,
@SerializedName("date") var date : String,
@SerializedName("place") var place : String,
@SerializedName("description") var description : String,
@SerializedName("price") var price :String
)