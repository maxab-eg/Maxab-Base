package com.itsmart.helpers

import android.util.Log

object Logger {
    var isDebug = false

    fun setMode(isDebugging: Boolean) {
        isDebug = isDebugging
    }

    fun d(key: String, value: String) {
        if (isDebug)
            Log.d(key, value)
    }

    fun e(key: String, value: String) {
        if (isDebug)
            Log.e(key, value)
    }

    fun w(key: String, value: String) {
        if (isDebug)
            Log.w(key, value)
    }

    fun v(key: String, value: String) {
        if (isDebug)
            Log.v(key, value)
    }

    fun i(key: String, value: String) {
        if (isDebug)
            Log.i(key, value)
    }
}