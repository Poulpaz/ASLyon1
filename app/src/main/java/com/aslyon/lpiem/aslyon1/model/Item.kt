package com.aslyon.lpiem.aslyon1.model

data class Item(
	val feed: Feed? = null,
	val items: List<ItemsItem?>? = null,
	val status: String? = null
)