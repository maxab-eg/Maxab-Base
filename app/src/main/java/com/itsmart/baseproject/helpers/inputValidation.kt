package com.itsmart.baseproject.helpers

import android.content.Context
import android.util.Patterns
import java.util.regex.Pattern

object inputValidation {

    /**
    TODO validation to all user inputs
     **/


    /**
     * This method is used to validate UserName.
     * called when handle validation of user email.
     * @param username of  user
     * @param message is error message to show
     * @return is valid or not
     */
    fun isValidUsername(
        context: Context, username: String?, message: String, regex: String = "^[a-zA-Z0-9._-]{3,20}$"
    ): Boolean {
        return when {
            isNullOrEmpty(username) -> false
            !Pattern.matches(regex, username) -> false
            else -> true
        }
        return false
    }

    /**
     * This method is used to validate email.
     * called when handle validation of user email.
     * @param email of email user
     * @param message is error message to show
     * @return is valid or not
     */
    fun isValidEmail(context: Context, email: String?, message: String): Boolean {
        return when {
            isNullOrEmpty(email) -> false
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> false
            else -> true
        }
        return false
    }

    /**
     * This method is used to validate mobile.
     * called when handle validation of user email.
     * @param mobile of  user
     * @param message is error message to show
     * @return is valid or not
     */
    fun isValidMobile(context: Context, mobile: String?, message: String, regex: String = "^[0-9]{10}$"): Boolean {
        return when {
            isNullOrEmpty(mobile) -> false
            mobile?.length != 11 -> false
            !Pattern.matches(regex, mobile) -> false
            else -> true
        }
        return false
    }

    /**
     * This method is used to validate password.
     * called when handle validation of user email.
     * @param password of user
     * @param message is error message to show
     * @return is valid or not
     */
    fun isValidPassword(context: Context, password: String?, message: String): Boolean {
        return when {
            isNullOrEmpty(password) -> false
            password!!.length < 6 -> false
            password.length > 30 -> false
            else -> true
        }
        return false
    }

    private fun isNullOrEmpty(input: String?): Boolean = input == null || input.isEmpty()
}