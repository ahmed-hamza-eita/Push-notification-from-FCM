package com.hamza

import android.app.Application
import android.content.Context
import com.hamza.utils.MySharedPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        MySharedPreferences.init(this)
    }

}