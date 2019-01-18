package com.aslyon.lpiem.aslyon1.datasource.request

import com.google.gson.annotations.SerializedName

class NotificationData(
        @SerializedName("title") var title: String,
        @SerializedName("description") var description: String
)