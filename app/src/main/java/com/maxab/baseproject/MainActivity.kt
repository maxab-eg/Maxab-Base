package com.maxab.baseproject

import android.os.Bundle
import com.maxab.baseproject.BaseActivity
import com.maxab.auth.LoginViewModel

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
