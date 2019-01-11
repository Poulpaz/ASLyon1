package com.aslyon.lpiem.aslyon1.model

class ItemsItem(
	val thumbnail: String? = null,
	val enclosure: Enclosure? = null,
	val author: String? = null,
	val link: String? = null,
	val guid: String,
	val description: String? = null,
	val categories: List<String?>? = null,
	val title: String? = null,
	val pubDate: String? = null,
	val content: String? = null
)