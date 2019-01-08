package com.aslyon.lpiem.aslyon1.datasource

import android.content.Context
import com.google.gson.JsonParser
import retrofit2.HttpException

/**
 * Author:        oronot
 * Creation date: 25/04/2018
 */
sealed class NetworkEvent {
    class Error(val e: Throwable) : NetworkEvent() {
        var codeError: Int = 0

        init {
            if (e is HttpException) {
                val string = e.response()
                        .errorBody()?.string()
                if (JsonParser().parse(string).asJsonObject.has("error")) {
                    this.codeError = JsonParser().parse(string)
                            .asJsonObject["error"]
                            .asInt
                }
            }
        }

        fun getErrorMessage(context: Context, defaultError: String? = null) : String {
            return "test"
            /*return when(codeError) {
//                1 -> context.getString(R.string.error1)
                2 -> context.getString(R.string.errorNoUser)
                3 -> context.getString(R.string.errorExistentMail)
                4 -> context.getString(R.string.errorInExistentMediumName)
                5 -> context.getString(R.string.errorConnexionError)
                6 -> context.getString(R.string.errorBlockedUser)
                7 -> context.getString(R.string.errorInExistentUser)
                10 -> context.getString(R.string.errorInExistentStory)
                11 -> context.getString(R.string.errorCommentNotOwned)
//                20 -> context.getString(R.string.error20)
                30 -> context.getString(R.string.errorPasswordDifferences)
//                40 -> context.getString(R.string.error40)
//                42 -> context.getString(R.string.error42)
                else -> defaultError ?: context.getString(R.string.error)
            }*/
        }


    }

    object InProgress : NetworkEvent()
    object Success : NetworkEvent()
    object None : NetworkEvent()
}