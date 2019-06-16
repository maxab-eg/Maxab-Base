package com.maxab.baseproject.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Response<T> {

    @SerializedName("data")
    @Expose
    val data: T? = null
    @SerializedName("status")
    @Expose
    val status: String? = null
    @SerializedName("status_code")
    @Expose
    val statusCode: Int? = null
}
