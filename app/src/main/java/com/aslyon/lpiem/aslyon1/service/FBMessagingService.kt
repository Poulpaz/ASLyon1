package com.emlyon.makersstories.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.renderscript.RenderScript
import android.util.Log
import androidx.core.app.NotificationCompat
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.ui.activity.AddTournamentActivity
import com.aslyon.lpiem.aslyon1.ui.activity.DetailsEventActivity
import com.aslyon.lpiem.aslyon1.ui.activity.DetailsEventActivity.Companion.ExtraEventId
import com.aslyon.lpiem.aslyon1.ui.activity.DetailsOfferActivity
import com.aslyon.lpiem.aslyon1.ui.activity.DetailsOfferActivity.Companion.ExtraOfferId
import com.aslyon.lpiem.aslyon1.ui.activity.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.squareup.picasso.Picasso
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

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Timber.d("From: " + remoteMessage.from)

        // Check if message contains a data payload.
        if (remoteMessage.notification != null && remoteMessage.data.isNotEmpty()) {
            Timber.d("Message data payload: " + remoteMessage.data)

            val title = remoteMessage.notification?.title
            val text = remoteMessage.notification?.body
            val id = remoteMessage.data["idNotif"]
            val type = remoteMessage.data["type"]

            // Create an explicit intent for an Activity in your app
            val intent = when(type){
                "event" -> {
                    Intent(this, DetailsEventActivity::class.java).putExtra(ExtraEventId, id?.toInt())
                }
                "offer" -> {
                    Intent(this, DetailsOfferActivity::class.java).putExtra(ExtraOfferId, id?.toInt())
                }
                else -> {
                    Intent(this, MainActivity::class.java)
                }
            }
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
            val mBuilder = NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText(text)
                    .setContentTitle(title)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setSmallIcon(R.drawable.logo_lyon)
                    .setAutoCancel(true)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = getString(R.string.channelName)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(getString(R.string.default_notification_channel_id), name, importance)
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                notificationManager.createNotificationChannel(channel)
            }

            // notificationId is a unique int for each notification that you must define
            val idNotification = System.currentTimeMillis().toInt()
            notificationManager.notify(idNotification, mBuilder.build())
        }
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
    }
}