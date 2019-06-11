package com.itsmart.baseproject

import android.content.Intent
import android.os.Bundle
import com.maxab.auth.LoginViewModel
import com.maxab.baseproject.R

class LoginActivity : BaseActivity() {
    override fun actionOnSuccess() {
        var intent = Intent(this, MainActivity::class.java)
        goToActivity(intent)
    }


    lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = LoginViewModel(this)
        showLoadingDialog()
        viewModel.Login("01151421688", "123456")
    }


}
