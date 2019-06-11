package com.itsmart.baseproject

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import com.google.gson.Gson
import com.itsmart.baseproject.Models.MessageResponse
import com.itsmart.baseproject.helpers.ApiHelper
import com.itsmart.baseproject.helpers.Logger
import com.itsmart.baseproject.helpers.SharedPref
import com.itsmart.baseproject.helpers.toast
import com.maxab.baseproject.Models.ErrorResponse
import com.maxab.baseproject.R
import com.maxab.baseproject.helpers.ResponseObserver
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException

abstract class BaseActivity : AppCompatActivity(),ApiHelper.ViewModelListener{
    var progressDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ButterKnife.bind(this)
    }

    override fun setContentView(layoutId: Int) {
        super.setContentView(layoutId)
    }


    abstract fun actionOnSuccess()

    override fun onSuccess() {
        actionOnSuccess()
    }

    override fun onError(response: Response<ResponseBody>) {
        onApiError(response)
    }

    override fun onFinish() {
        dismissLoadingDialog()
    }

    fun showLoadingDialog() {
        dismissLoadingDialog()
        try {
            progressDialog = Dialog(this)
            progressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            progressDialog!!.setContentView(R.layout.dialog_prog)
            progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progressDialog!!.setCancelable(false)
            progressDialog!!.show()
        } catch (e: Exception) {

        }

    }

    fun dismissLoadingDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            try {
                progressDialog!!.dismiss()
            } catch (e: Exception) {
            }

        }
    }

    fun goToActivity(intent: Intent) {

        intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
        startActivity(intent)
    }

    fun logout() {
        var intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        SharedPref.clearData()
        startActivity(intent)
    }

    fun goToFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun errorMessage(message: String) {
        toast.showShortToaster(this, message)
    }

    open fun handleBadRequest(){

    }

    open fun onApiError(response: Response<ResponseBody>) {
        var responseError = ""
        val code = response.code()

        when (code) {
            ApiHelper.BAD_REQUEST -> {
                handleBadRequest()
            }
            ApiHelper.FORCE_UPDATE -> {
                // go to force update activity
            }
            ApiHelper.BLOCK -> {
                // go to block activity
            }
            ApiHelper.UNAUTHORIZE -> {

            }

            ApiHelper.FORBIDDEN -> try {

            } catch (e: IOException) {
                e.printStackTrace()
            }

            else ->{
                errorMessage(responseError)
            }
        }
    }
}
