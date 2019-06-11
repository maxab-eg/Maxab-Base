package com.maxab.baseproject.helpers

import android.util.Log
import android.widget.Toast
import com.google.gson.Gson

import com.maxab.baseproject.app.App
import com.maxab.baseproject.helpers.toast
import com.maxab.baseproject.Models.ErrorResponse
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.internal.util.HalfSerializer.onComplete
import retrofit2.Response

import java.io.IOException

abstract class ResponseObserver<T> : SingleObserver<Response<T>> {

    override fun onSubscribe(d: Disposable) {
        Log.d(TAG, "onSubscribe")
    }

    override fun onSuccess(response: Response<T>) {
        try {
            if (response.isSuccessful) {
                onNext(response.body())
            } else {
                val error = response.errorBody()!!.string()
              //  onError(ResponseException(response.code(), error))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

       // onComplete()
    }

    override fun onError(e: Throwable) {
//        try {
//            if (e is IOException) {
//                onIOException()
//            } else if (e is ResponseException) {
//               // onApiException(e)
//            }
//            onComplete()
//        } catch (ex: Exception) {
//            e.printStackTrace()
//        }

    }

    protected abstract fun onNext(data: T?)

    companion object {
        private val TAG = "ResponseObserver"
    }
}
