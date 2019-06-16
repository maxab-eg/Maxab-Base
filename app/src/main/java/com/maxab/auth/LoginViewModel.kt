package com.maxab.auth

import com.maxab.baseproject.BaseViewModel
import com.maxab.baseproject.helpers.ApiHelper
import com.maxab.baseproject.helpers.ApiUrl
import com.maxab.baseproject.helpers.SharedPref
import io.reactivex.Single
import okhttp3.ResponseBody

class LoginViewModel : BaseViewModel() {

    fun Login(phone: String, password: String): Single<ResponseBody?>? {
        var query = HashMap<String, String>()
        query.put("mobile", phone)
        query.put("password", password)

        var call = ApiHelper.getMethod(ApiUrl.LOGIN, query)

        return call
            .doOnSubscribe {
                loadingOn()
            }
            .doOnSuccess { response ->
                loadingOff()
                SharedPref.setUserData(response.body()!!.string(), response.body().toString())
            }
            .doOnError { loadingOff() }
            .map { it.body() }
    }
}


//            .subscribe({ response ->
//            var code = response.code()
//
//            Logger.d("retrofit_url", code.toString() + " : " + response.raw().request().url().toString())
//            if (response.code() == SUCCESS) {
//                SharedPref.setUserData(response.body()!!.string(), response.body().toString())
//                listner.onSuccess()
//            } else {
//                listner.onError(response)
//
//            }
//
//            listner.onFinish()
//        }, { error ->
//            if (error.message != null) {
//                Logger.d("retro_exception", error.message.toString())
//            }
//        })
