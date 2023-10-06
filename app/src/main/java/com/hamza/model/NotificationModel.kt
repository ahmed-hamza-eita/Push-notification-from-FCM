package com.hamza.model

/*
"to":user device token (person I send to him)
"data":{
    "title" :"Hello",
    "message" :"hello world"
        }
*/

data class NotificationModel(
    val to: String,
    val data: Data
)

data class Data(
    val title: String,
    val message: String
)