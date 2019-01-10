package com.aslyon.lpiem.aslyon1.datasource.response

import com.google.gson.annotations.SerializedName

open class BaseResponse(
        @SerializedName("message") var message: String?
)