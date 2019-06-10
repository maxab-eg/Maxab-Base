package com.maxab.baseproject.helpers

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.onesignal.OSNotification
import com.onesignal.OneSignal

class NotificationHandler(internal var context: Context) : OneSignal.NotificationReceivedHandler {
    internal var user_token: String? = null
    private val TAG = "NotificationHandler"


    override fun notificationReceived(notification: OSNotification) {
        Log.d(TAG, "notificationReceived() called with: notification = [$notification]")
        Log.i(TAG, "Body : " + notification.payload.body)
        Log.i(TAG, "Title : " + notification.payload.title)
        Log.i(TAG, "ID : " + notification.payload.notificationID)


    }
}