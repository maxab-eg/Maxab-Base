package com.itsmart.helpers

import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import retrofit2.Response

import java.io.IOException

abstract class ResponseObserver<T> : SingleObserver<Response<T>> {

    override fun onSubscribe(d: Disposable) {
        Log.d(TAG, "onSubscribe")
    }

    override fun onSuccess(response: Response<T>) {
        try {
            if (response.isSuccessful && response.code() != 463) {
                onNext(response.body())
            } else {
                val error = response.errorBody()!!.string()
                onError(ResponseException(response.code(), error))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        onComplete()
    }

    override fun onError(e: Throwable) {
        try {
            if (e is IOException) {
                onIOException()
            } else if (e is ResponseException) {
                onApiException(e)
            }
            onComplete()
        } catch (ex: Exception) {
            e.printStackTrace()
        }

    }

    fun onMessage(message: String?) {
        //if (message!!.length != 0 && message != null)
         //   Toast.makeText(App.INSTANCE, message, Toast.LENGTH_LONG).show()
    }

    protected abstract fun onNext(data: T?)

    fun onApiException(e: ResponseException) {
//        if (e.code == 500) {
//            onMessage("خطا فى الانترنت")
//        } else {
//            val error = e.error<ErrorResponse>(ErrorResponse::class.java)
//            onMessage(error.getMessage())
//        }
    }

    protected fun onIOException() {
        onMessage("خطا فى الانترنت")
    }

    protected fun onComplete() {
        Log.d(TAG, "onComplete(")
    }

    class ResponseException : Throwable {

        var code: Int? = 0

        constructor(code: Int, message: String) : super(message) {
            this.code = code
            Log.d(TAG, "ResponseException")
        }

        constructor(cause: Throwable) : super(cause) {}

        fun code(): Int? {
            return code
        }

        fun <T> error(aClass: Class<T>): T {
            return Gson().fromJson(message, aClass)
        }
    }

    companion object {
        private val TAG = "ResponseObserver"
    }
}
