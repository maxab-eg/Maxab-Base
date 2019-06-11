package com.itsmart.baseproject.helpers

import android.content.Context
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.itsmart.baseproject.Models.MessageResponse
import com.maxab.baseproject.BuildConfig
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT


object RefreshToken {


    class FirebaseTokenBody(
        @field:SerializedName("fireBaseToken")
        @field:Expose
        internal var token: String, @field:SerializedName("deviceId")
        @field:Expose
        internal var deviceId: String
    )

    interface RefreshFirebaseApi {
        @PUT("update_fcm_token")
        fun refreshFirebase(@Header("Authorization") token: String, @Body firebaseTokenBody: FirebaseTokenBody): Call<MessageResponse>
    }

    fun refreshToken(context: Context): Call<DataTokenResponse> {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()

        val api = retrofit.create(refreshTokenAPI::class.java)

        val request = TokenRequest()
        val token = SharedPref.getAccessToken()
        request.setToken(token)

        return api.getToken(request)
    }

    class TokenResponse {

        @SerializedName("token")
        @Expose
        var token: String? = null
    }

    class DataTokenResponse {
        @SerializedName("msg")
        @Expose
        val msg: String? = null

        @SerializedName("data")
        @Expose
        val tokenResponse: TokenResponse? = null
    }

    class TokenRequest {
        @SerializedName("device_id")
        @Expose
        var deviceId: String? = null
        @SerializedName("expired_token")
        @Expose
        private var token: String? = null

        fun setToken(token: String) {
            this.token = token
        }
    }


    interface refreshTokenAPI {

        @POST("api/retailer-app/refresh-token")
        fun getToken(
            @Body request: TokenRequest
        ): Call<DataTokenResponse>
    }

}
