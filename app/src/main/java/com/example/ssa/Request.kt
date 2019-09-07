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

//data class
data class SampleData(val names:String ,val date:String ,val title:String)

//viewHolder
data class ViewHolder(
    val nameTextView: TextView,
    val dateTextView: TextView,
    val titleTextView: TextView
)
