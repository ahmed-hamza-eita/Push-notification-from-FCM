package com.hamza.notification

import android.annotation.SuppressLint
import android.app.Application

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.media.RingtoneManager
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import androidx.core.app.NotificationCompat


import com.hamza.pushnotificationfromfcm.R
import com.hamza.ui.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.random.Random


class MyNotificationManager @Inject constructor(private val mContext: Application ) {

    @SuppressLint("ObsoleteSdkInt", "MissingPermission")
    fun sendNotification(title: String?, message: String?) {
        val notificationId = Random.nextInt(100000)

        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            0
        }

        val spannable = SpannableString(title)
        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            title!!.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        var intent: Intent? = null
        if (true) {
            intent = Intent(mContext, MainActivity::class.java)
        }
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            mContext,
            0, intent, flag
        )
        val channelId = "default notification id"
        val vibrate = longArrayOf(0, 1000, 500, 1000)
        val notificationManager = mContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager


        //  val notificationManager = NotificationManagerCompat.from(mContext)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Main notification",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.apply {
                description = "test channel"
                enableLights(true)
                lightColor = Color.BLUE

                vibrationPattern = vibrate
            }
            notificationManager.createNotificationChannel(channel)
        }

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)

        val notificationBuilder = NotificationCompat.Builder(mContext, channelId)
            .setSmallIcon(R.drawable.baseline_circle_notifications_24)
            .setVibrate(vibrate)
            .setStyle(
                NotificationCompat.InboxStyle()
                    .setBigContentTitle(spannable)
                    .addLine(message)
            ).setContentText(message)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        notificationManager.notify(notificationId, notificationBuilder.build())
    }


}
