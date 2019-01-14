package com.emlyon.makersstories.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber


/**
 * Author:        oronot
 * Creation date: 24/05/2018
 */
class FBMessagingService : FirebaseMessagingService() {

    override fun onNewToken(s: String?) {
        super.onNewToken(s)
        Log.e("NEW_TOKEN", s)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
    }
}