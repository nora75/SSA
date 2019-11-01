package com.example.ssa

data class RegisterRespone(
    val user_id:String,
    val group_id:String
    //val meta :Meta,
    //val data:List<registerinfo>
)

data class GetDataListResponse(
    var UserID: Int,
    var UserName:String,
    var GroupID:String,
    var DataName:String,
    var ImageName:String,
    var Title:String,
    var DataType:Int
)


