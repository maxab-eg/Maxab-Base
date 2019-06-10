package com.itsmart.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Resource<T> {

    @SerializedName("success")
    @Expose
    var success: Boolean? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("data")
    @Expose
    var data: T? = null

    override fun toString(): String {
        return "Resource{" +
                "success=" + success +
                ", message='" + message + '\''.toString() +
                ", data=" + data +
                '}'.toString()
    }
}
