package com.maxab.baseproject

import com.maxab.baseproject.helpers.ApiHelper
import com.maxab.baseproject.helpers.ApiUrl
import io.reactivex.Single
import okhttp3.ResponseBody

class SignupViewModel : BaseViewModel() {

    fun signup(): Single<ResponseBody?>? {
        var query = HashMap<String, String>()

        var call = ApiHelper.getMethod(ApiUrl.SIGNUP, query)

        return call
            .doOnSubscribe {
                loadingOn()
            }
            .doOnSuccess { response ->
                loadingOff()
            }
            .doOnError { loadingOff() }
            .map { it.body() }
    }
}