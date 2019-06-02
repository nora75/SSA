package com.example.ssa

import android.text.format.DateFormat
import java.util.Date

class GetName {

    private var date: String = "" // Init
    private val id: String = "1" // 下記が他担当により、ダミーのものになる。
//    private val id: String = Db.getId() // IDをDBから取得する事により被らないように。

    /*
    * ファイルの名前をID,現在時刻より返す。
     */
    fun get(): String {
        date = DateFormat.format("yyyy-MM-dd_kk-mm-ss", Date()).toString() //  現在時刻を取得し、特定のフォーマットで返す。(取得)
        return """_${id}_$date"""
    }
}

