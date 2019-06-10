package com.itsmart.baseproject.helpers

import android.annotation.SuppressLint
import android.app.Activity
import com.google.gson.Gson
import com.itsmart.baseproject.Models.MessageResponse
import com.itsmart.baseproject.BuildConfig
import com.itsmart.baseproject.R
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.io.IOException
import java.util.HashMap
import java.util.concurrent.TimeUnit

object ApiHelper {

    // TODO : increase when release
    var version = 1
    val take = 10


    // timeout for http connection
    val TIMEOUT_SEC = 30

    // when app version is old
    val FORCE_UPDATE = 451

    // when user blocked
    val BLOCK = 456

    // when token expired
    val REFRESH_TOKEN = 463

    // data not send correctly
    val BAD_REQUEST = 400


    // data not send correctly
    val UNAUTHORIZE = 401


    // data not send correctly
    val FORBIDDEN = 403


    // connect to server success
    val SUCCESS = 200


    // connect to server created
    val CREATED = 201

    fun getHeaders(currentActivity: Activity): Map<String, String> {
        val headers = HashMap<String, String>()
        headers["Version"] = version.toString()
        headers["Authorization"] = SharedPref.getAccessToken(currentActivity)

        return headers
    }

    fun postMethod(
        currentActivity: Activity,
        url: String,
        requestBody: RequestBody?
    ): Single<retrofit2.Response<ResponseBody>> {
        var requestBody = requestBody


        val client = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
            .build()


        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .build()

        val apiInterface = retrofit.create(ApiInterface::class.java!!)

        Logger.d("retrofit_body", Gson().toJson(requestBody))
        if (requestBody == null)
            requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), "{}")


        val headers = getHeaders(currentActivity)

        return apiInterface.postMethod(headers, url, requestBody)
    }

    fun putMethod(
        currentActivity: Activity,
        url: String,
        requestBody: RequestBody?
    ): Single<retrofit2.Response<ResponseBody>> {
        var requestBody = requestBody


        val client = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
            .build()


        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .build()

        Logger.d("retrofit_body", Gson().toJson(requestBody))
        if (requestBody == null)
            requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), "{}")


        val apiInterface = retrofit.create(ApiInterface::class.java!!)

        val headers = getHeaders(currentActivity)

        return apiInterface.putMethod(headers, url, requestBody)
    }

    fun getMethod(
        currentActivity: Activity,
        url: String,
        options: Map<String, String>?
    ): Single<retrofit2.Response<ResponseBody>> {
        var options = options


        val client = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
            .build()


        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .build()


        if (options == null)
            options = HashMap()

        val headers = getHeaders(currentActivity)

        return retrofit.create(ApiInterface::class.java)
            .getMethod(headers, url, options)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    @SuppressLint("CheckResult")
    fun handleApiResponses(
        currentActivity: Activity,
        call: Single<retrofit2.Response<ResponseBody>>,
        listener: ApiResponseListener?
    ) {
        call.subscribe({ response ->
            var responseError = ""
            val code = response.code()
            var body = MessageResponse("")

            if (response.code() !== SUCCESS && response.code() !== CREATED) {

                try {
                    responseError = response.body()!!.string()
                    body = Gson().fromJson<Any>(responseError, MessageResponse::class.java) as MessageResponse

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
            Logger.d("retrofit_url", code.toString() + " : " + response.raw().request().url().toString())
            Logger.d("retrofit_error", responseError + "")
            when (code) {
                BAD_REQUEST ->
                    listener!!.onError(responseError)
                FORCE_UPDATE -> {

                }
                BLOCK -> {
                }
                UNAUTHORIZE -> {

                }

                FORBIDDEN -> try {
                    listener!!.onError(response.body()!!.string())
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                SUCCESS, CREATED -> try {

                    listener!!.onSuccess(response.body()!!.string())

                } catch (e: Exception) {

                }

            }
            listener!!.onFinish()
        }, { error ->
            if (error.message != null) {
                Logger.d("retro_exception", error.message.toString())
            }
        }

        )

//    enqueue(
//    object : Callback<Resource<String>>() {
//        fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//
//        }
//
//        fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//            listener!!.onFinish()
//                Dialogs.dismissLoadingDialog()
//                if (!Utility.isInternetAvailable())
//                    if (t.message != null) {
//                        Logger.d("retro_exception", t.message)
//
//                        if (t.message != "Canceled")
//                            Dialogs.showToast(
//                                currentActivity.resources.getString(R.string.something_wrong),
//                                currentActivity
//                            )
//
//                    } else
//                        Dialogs.showToast(currentActivity.resources.getString(R.string.no_internet), currentActivity)
//
//        }
//    })
    }


    interface ApiInterface {

        @GET
        fun getMethod(
            @HeaderMap headers: Map<String, String>,
            @Url uri: String,
            @QueryMap options: Map<String, String>
        ): Single<retrofit2.Response<ResponseBody>>

        @POST
        fun postMethod(
            @HeaderMap headers: Map<String, String>,
            @Url uri: String,
            @Body request: RequestBody?
        ): Single<retrofit2.Response<ResponseBody>>

        @PUT
        fun putMethod(
            @HeaderMap headers: Map<String, String>,
            @Url uri: String,
            @Body request: RequestBody?
        ): Single<retrofit2.Response<ResponseBody>>

    }

    interface ApiResponseListener {
        fun onRefresh()

        fun onSuccess(response: String)

        fun onError(response: String)

        fun onFinish()
    }

    interface ResponsesListener {
        fun onSuccess(response: String)

        fun onFinish()
    }

    interface ResponseSuccessListener {
        fun onSuccess(response: String)
    }

    interface ResponseErrorListener {
        fun onError(response: String)
    }
}