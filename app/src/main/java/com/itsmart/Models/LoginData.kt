package atiaf.redstone.Models

/**
 * Created by BEST BUY on 6/4/2018.
 */

data class LoginData(
    val data: DataLogin,
    val token: String,
    val expire: Long
)

data class DataLogin(
    val name: String,
    val email: String,
    val mobile: String,
    val accountType: Int,
    val accountTypeTitle: String,
    val image: String
)