package com.itsmart.baseproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.itsmart.helpers.SharedPref
import com.itsmart.helpers.inputValidation.isValidEmail

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}
