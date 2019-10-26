package com.example.ssa

import android.widget.TextView

data class RegisterRequest(
    val user_name: String,
    val password: String,
    val mail: String,
    val group_id: String
)

data class LoginRequest(
    val mail: String,
    val password: String
)

data class WriteRequest(
    val id:String,
    val data_name:String,
    val data_type:String
)

data class MultSampleRequest(
//    val data_name: String,
//    val data_type: String,
    val title:String,
    val user_id:String
)

data class DataRequest(
    val user_id: String
)

data class RenewList(
    val user_id: Int
)

data class LogoutRequest(
    val password: String
)
