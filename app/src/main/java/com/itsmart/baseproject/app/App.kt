package com.itsmart.baseproject.app

import android.app.Application
import android.content.pm.ApplicationInfo
import androidx.appcompat.app.AppCompatDelegate
import com.itsmart.baseproject.helpers.Logger

class App : Application() {

    val isDebuggable: Boolean
        get() = 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        Logger.setMode(isDebuggable)

    }
}

