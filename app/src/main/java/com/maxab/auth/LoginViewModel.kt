package com.maxab.auth

import androidx.lifecycle.ViewModel
import com.maxab.baseproject.helpers.ApiHelper
import com.maxab.baseproject.helpers.ApiHelper.SUCCESS
import com.maxab.baseproject.helpers.Logger
import com.maxab.baseproject.helpers.SharedPref

class LoginViewModel(listner: ApiHelper.ViewModelListener) : ViewModel() {
    var url: String = "loginUrl"
    var listner = listner


    fun Login(phone: String, password: String) {
        var query = HashMap<String, String>()
        query.put("mobile", phone)
        query.put("password", password)
        var call = ApiHelper.getMethod("api/retailer-app/login", query)
        call.subscribe({ response ->
            var code = response.code()

            Logger.d("retrofit_url", code.toString() + " : " + response.raw().request().url().toString())
            if (response.code() == SUCCESS) {
                SharedPref.setUserData(response.body()!!.string(), response.body().toString())
                listner.onSuccess()
            } else {
                listner.onError(response)

            }

            listner.onFinish()
        }, { error ->
            if (error.message != null) {
                Logger.d("retro_exception", error.message.toString())
            }
        })

    }
}