package com.itsmart.baseproject

import android.os.Bundle
import com.maxab.auth.LoginViewModel
import com.maxab.baseproject.R

class MainActivity : BaseActivity() {
    override fun actionOnSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

}
