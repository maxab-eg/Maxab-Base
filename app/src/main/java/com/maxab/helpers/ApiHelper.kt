package com.maxab.helpers

import android.app.Activity
import com.google.gson.Gson
import com.maxab.Models.MessageResponse
import com.maxab.baseproject.BuildConfig
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import retrofit2.Retrofit
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

    fun postMethod(currentActivity: Activity, url: String, requestBody: RequestBody?): Observable<ResponseBody> {
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

    fun putMethod(currentActivity: Activity, url: String, requestBody: RequestBody?): Observable<ResponseBody> {
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

//    fun getMethod(currentActivity: Activity, url: String, options: Map<String, String>?): Single<ResponseBody> {
//        var options = options
//
//
//        val client = OkHttpClient.Builder()
//            .connectTimeout(TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
//            .writeTimeout(TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
//            .readTimeout(TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
//            .build()
//
//
//        val retrofit = Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl(BuildConfig.BASE_URL)
//            .client(client)
//            .build()
//
//        if (options == null)
//            options = HashMap()
//
//        val apiInterface = retrofit.create(ApiInterface::class.java!!)
//
//        val headers = getHeaders(currentActivity)
//
//        return apiInterface.getMethod(headers, url, options)
//    }

    fun getMethod(currentActivity: Activity, url: String, options: Map<String, String>?): Observable<ResponseBody> {
        var options = options


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


        if (options == null)
            options = HashMap()

        val headers = getHeaders(currentActivity)

        return retrofit.create(ApiInterface::class.java)
            .getMethod(headers, url, options)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

//    @SuppressLint("CheckResult")
//    fun handleApiResponses(currentActivity: Activity, call: Observable<ResponseBody>, listener: ApiResponseListener?) {
//
//
//        call.subscribe(
//            { result ->
//                val body: ResponseBody = result.().body()
//                val text: String = body.string()
//            }
//            )

//    enqueue(
//    object : Callback<Resource<String>>() {
//        fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

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

//        }
//    })


    fun handleResponse(response: Response, listener: ApiResponseListener?) {
        var responseError = ""
        val code = response.code()
        var body = MessageResponse("")

        if (response.code() !== ApiHelper.SUCCESS && response.code() !== ApiHelper.CREATED) {

            try {
                responseError = response.body()!!.string()
                body = Gson().fromJson<Any>(responseError, MessageResponse::class.java) as MessageResponse

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        Logger.d("retrofit_url", code.toString() + " : " + response.request().url().toString())
        Logger.d("retrofit_error", responseError + "")
        when (code) {
            ApiHelper.BAD_REQUEST ->

                listener!!.onError(responseError)
            ApiHelper.FORCE_UPDATE -> {
            }
            ApiHelper.BLOCK -> {
            }
            ApiHelper.UNAUTHORIZE -> {
            }

            ApiHelper.FORBIDDEN -> try {
                listener!!.onError(response.body()!!.string())
            } catch (e: IOException) {
                e.printStackTrace()
            }

//            ApiHelper.REFRESH_TOKEN -> RefreshToken.refreshToken(currentActivity).enqueue(object :
//                Callback<RefreshToken.DataTokenResponse>() {
//                fun onResponse(
//                    call: Call<RefreshToken.DataTokenResponse>,
//                    response: Response<RefreshToken.DataTokenResponse>
//                ) {
//                    if (response.code() === ApiHelper.SUCCESS && response.body() != null) {
//                        val sharedPref = SharedPref(currentActivity)
//                        sharedPref.setToken(response.body().getTokenResponse().getToken())
//
//                        listener?.onRefresh()
//                    }
//                }
//
//                fun onFailure(call: Call<RefreshToken.DataTokenResponse>, t: Throwable) {
//
//                }
//            })
            ApiHelper.SUCCESS, ApiHelper.CREATED -> try {

                listener!!.onSuccess(response.body()!!.string())

            } catch (e: Exception) {

            }

//                    else -> Dialogs.showToast(response.message(), currentActivity)
        }
        //               Dialogs.dismissLoadingDialog()
        listener!!.onFinish()
    }


    interface ApiInterface {

        @GET
        fun getMethod(
            @HeaderMap headers: Map<String, String>,
            @Url uri: String,
            @QueryMap options: Map<String, String>
        ): Observable<ResponseBody>

        @POST
        fun postMethod(
            @HeaderMap headers: Map<String, String>,
            @Url uri: String,
            @Body request: RequestBody?
        ): Observable<ResponseBody>

        @PUT
        fun putMethod(
            @HeaderMap headers: Map<String, String>,
            @Url uri: String,
            @Body request: RequestBody?
        ): Observable<ResponseBody>

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