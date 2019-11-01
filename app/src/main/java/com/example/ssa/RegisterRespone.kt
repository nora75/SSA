package com.example.ssa

data class RegisterRespone(
    val user_id:String,
    val group_id:String
)

data class test(
    var UserID:String,
    var UserName:String,
    var GroupID:String,
    var DataName:String,
    var ImageName:String,
    var Title:String,
    var DataType:String
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

data class List<T>(
    val datalist:GetDataListResponse
)
/*
data class GetDataListResponse(
    val UserID:ArrayList<Int>,
    val UserName:ArrayList<String>,
    val GroupID:ArrayList<String>,
    val DataName:ArrayList<String>,
    val ImageName:ArrayList<String>,
    val Title:ArrayList<String>,
    val DataType:ArrayList<Int>
)


*/