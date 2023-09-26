package com.capgemini.starterkit.roomdatabase

import android.app.Application

class MainApplication : Application() {
    companion object {
        @Volatile
        private var INSTANCE: MainApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}