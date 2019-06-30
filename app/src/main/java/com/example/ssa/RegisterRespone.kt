package com.example.ssa

data class RegisterRespone(
    //val id:String,
    val group_id:String
    //val meta :Meta,
    //val data:List<registerinfo>
)

data class Meta(
    val status:String,
    val message:String
)

data class registerinfo(
    //val id:String,
    val group_id:String
)

data class LoginRespone(
    //val meta:Meta,
    //val data: loginfo
    val boolean: Boolean
)

data class loginfo(
    val boolean: Boolean
)

