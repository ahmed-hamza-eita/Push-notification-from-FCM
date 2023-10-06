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
        "Authorization: key=AAAAh9kC_hI:APA91bH5tot1CVbay6uA_qm6RDYj3aIK1FlIxXtDjKkZGbvqRPifjLwehKhoAhEBsz9r53XoxWsp1YQuVFhraWIWh5cdd5NdIVHdGaADbP8HVfyW5tIrMqhxqwhw6WIEGj8AnPgm6Pz0",
        "Content-Type:application/json"
    )

    @POST("fcm/send")
    suspend fun sendNotification(@Body notificationModel: NotificationModel): Response<ResponseBody?>
}