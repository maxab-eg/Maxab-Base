package com.maxab.baseproject.helpers

import android.app.Application
import android.content.Intent
import com.itsmart.baseproject.MainActivity
import com.onesignal.OSNotificationOpenResult
import com.onesignal.OneSignal

class MyNotificationOpenedHandler(private val application: Application) : OneSignal.NotificationOpenedHandler {

    override fun notificationOpened(result: OSNotificationOpenResult) {
        startApp()
    }

    private fun startApp() {

        val intent = Intent(application, MainActivity::class.java)
        application.startActivity(intent)
    }
}