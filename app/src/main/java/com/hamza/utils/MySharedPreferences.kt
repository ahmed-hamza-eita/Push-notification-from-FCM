package com.hamza.utils

import android.content.Context
import android.content.SharedPreferences


object MySharedPreferences {


    private var mAppContext: Context? = null
    private const val SHARED_PREFERENCES_NAME = "data"
    private const val USER_TOKEN = "data"


    private fun mySharedPreference() {}


    fun init(appContext: Context?) {
        mAppContext = appContext
    }

    private fun getSharedPreferences(): SharedPreferences {
        return mAppContext!!.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }


    fun setUserToken(token: String) {
//when login or signup in app set user token and store it here
        val editor = getSharedPreferences().edit()
        editor.putString(USER_TOKEN, token).apply()

    }

    fun getUserToken(): String? {
        return getSharedPreferences().getString(USER_TOKEN, "")


    }


}