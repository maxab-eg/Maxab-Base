package com.itsmart.baseproject.helpers

import android.content.Context
import android.content.SharedPreferences
import atiaf.redstone.Models.UserModel
import com.google.gson.Gson
import com.itsmart.baseproject.app.App


/**
 * Created by ATIAF on 3/14/2018.
 */

object SharedPref {
    private val USER_DATA_KEY = "userData"
    private val IS_LOGIN_KEY = "isLogin"
    private val ACCESS_TOKEN_KEY = "accessTokenKey"

    private var loginFile: SharedPreferences? = null


    fun initLoginSharedPreference(app: App) {
        loginFile = app.getSharedPreferences("loginFile", Context.MODE_PRIVATE)
    }

    fun setUserData(json: String, accessToken: String) {
        val editor = loginFile!!.edit()
        editor.putString(USER_DATA_KEY, json)
        editor.putString(ACCESS_TOKEN_KEY, accessToken)
        editor.putBoolean(IS_LOGIN_KEY, true)
        editor.apply()
    }

    open fun getUserData(): UserModel {
        val gson = Gson()
        val json = loginFile!!.getString(USER_DATA_KEY, "")
        return gson.fromJson<UserModel>(json, UserModel::class.java!!)
    }


    fun isLoggedIn(): Boolean {
        return loginFile!!.getBoolean(IS_LOGIN_KEY, false)
    }

    fun getAccessToken(): String {
        return loginFile!!.getString(ACCESS_TOKEN_KEY, "")
    }

    fun clearData() {
        val editor = loginFile!!.edit()
        editor.clear()
        editor.apply()
    }

}
