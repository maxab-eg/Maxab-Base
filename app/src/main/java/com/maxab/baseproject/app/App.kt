package com.maxab.baseproject.app

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import androidx.appcompat.app.AppCompatDelegate
import com.maxab.baseproject.helpers.Logger
import com.maxab.baseproject.helpers.SharedPref
import com.maxab.baseproject.helpers.MyNotificationOpenedHandler
import com.maxab.baseproject.helpers.NotificationHandler
import com.onesignal.OneSignal

class App : Application() {
    val isDebuggable: Boolean
        get() = 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        SharedPref.initLoginSharedPreference(this)

        Logger.setMode(isDebuggable)

        OneSignal.startInit(this)
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .unsubscribeWhenNotificationsAreDisabled(true)
            .setNotificationReceivedHandler(NotificationHandler(this))
            .setNotificationOpenedHandler(MyNotificationOpenedHandler(this))
            .init()

    }
}

