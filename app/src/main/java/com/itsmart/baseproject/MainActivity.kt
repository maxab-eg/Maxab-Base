package com.itsmart.baseproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.itsmart.baseproject.helpers.ApiHelper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val requestBody = HashMap<String,String>()
        requestBody.put("mobile","01151421688")
        requestBody.put("password","1233456")

        val call = ApiHelper.getMethod(this, "api/retailer-app/login", requestBody)

        ApiHelper.handleApiResponses(this, call, object : ApiHelper.ApiResponseListener {
            override fun onRefresh() {

            }

            override fun onSuccess(response: String) {

            }

            override fun onError(response: String) {

            }

            override fun onFinish() {

            }
        })

    }
}
