package com.hamza.notification

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.remoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyFirebaseMessegingService : FirebaseMessagingService() {
    @Inject
    lateinit var notificationManager: MyNotificationManager
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.data.isNotEmpty()) {
            try {

                val title = remoteMessage.data["title"]
                val message = remoteMessage.data["message"]

                notificationManager.sendNotification(
                    title, message
                )
            } catch (e: FirebaseException) {
                e.printStackTrace()
            }
        }
    }


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e("TAG", "onNewToken: ")
    }
}