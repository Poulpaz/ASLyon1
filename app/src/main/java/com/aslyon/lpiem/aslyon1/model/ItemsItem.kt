package com.aslyon.lpiem.aslyon1.model

import com.google.gson.annotations.SerializedName

class ItemsItem(
		@SerializedName("creator") val author: String,
		@SerializedName("link") val link: String,
		@SerializedName("title") val title: String,
		@SerializedName("pubDate") val pubDate: String
)