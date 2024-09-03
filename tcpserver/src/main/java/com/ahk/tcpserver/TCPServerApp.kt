package com.ahk.tcpserver

import android.app.Application
import timber.log.Timber

class TCPServerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.d("onCreate in Application.kt")
    }
}
