package com.example.ssa

import java.io.ByteArrayInputStream

data class RegisterRespone(
    val user_id:String,
    val group_id:String
)

class GetDataListResponse(
    var UserID: String = "",
    var UserName: String = "",
    var GroupID: String = "",
    var DataName:String="",
    var ImageName:String="",
    var Title:String="",
    var DataType:String=""
)

class GetDataResponse(
    var dataname:String,
    var data_type:Int,
    var image_name:String,
    var title:String
)