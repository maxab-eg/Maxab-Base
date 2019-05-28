package com.itsmart.helpers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import atiaf.redstone.Models.DataLogin
import atiaf.redstone.Models.UserModel

import com.google.gson.Gson

import java.util.ArrayList


/**
 * Created by ATIAF on 3/14/2018.
 */

class SharedPref {
    private val USER_DATA_KEY = "userData"
    private val IS_LOGIN_KEY = "isLogin"
    private val ACCESS_TOKEN_KEY = "accessTokenKey"

    private var loginFile: SharedPreferences? = null


    private fun initLoginSharedPreference(context: Context) {
        loginFile = context.getSharedPreferences("loginFile", Context.MODE_PRIVATE)
    }

    fun setUserData(activity: Activity, user: DataLogin, accessToken: String) {
        initLoginSharedPreference(activity)
        val editor = loginFile!!.edit()
        val gson = Gson()
        val json = gson.toJson(user)
        editor.putString(USER_DATA_KEY, json)
        editor.putString(ACCESS_TOKEN_KEY, accessToken)
        editor.putBoolean(IS_LOGIN_KEY, true)
        editor.apply()
    }

    fun getUserData(activity: Context?): UserModel {
        if (activity != null) {
            initLoginSharedPreference(activity)
        }
        val gson = Gson()
        val json = loginFile!!.getString(USER_DATA_KEY, "")
        return gson.fromJson<UserModel>(json, UserModel::class.java!!)
    }


    fun isLoggedIn(activity: Activity): Boolean {
        initLoginSharedPreference(activity)
        return loginFile!!.getBoolean(IS_LOGIN_KEY, false)
    }

    fun getAccessToken(activity: Context): String {
        initLoginSharedPreference(activity)
        return loginFile!!.getString(ACCESS_TOKEN_KEY, "")
    }

    fun clearData(activity: Activity) {
        initLoginSharedPreference(activity)
        val editor = loginFile!!.edit()
        editor.clear()
        editor.apply()
    }

}
