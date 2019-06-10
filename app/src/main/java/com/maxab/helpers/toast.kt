package com.maxab.helpers

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar


object toast {


    fun showLongToaster(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showShortToaster(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showSnack(view: View, context: Context, message: String) {
        val snack = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        snack.show()
    }
}