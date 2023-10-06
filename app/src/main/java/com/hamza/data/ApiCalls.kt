package com.hamza.data

import com.hamza.model.NotificationModel
import com.hamza.utils.MySharedPreferences
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiCalls {

    // this is my own token i used it for testing
    @Headers(
        "Authorization: key=YOUR TOKEN"
        ,
        "Content-Type:application/json"
    )

    @POST("fcm/send")
    suspend fun sendNotification(@Body notificationModel: NotificationModel): Response<ResponseBody?>
}