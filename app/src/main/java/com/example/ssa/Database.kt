package com.example.ssa

import android.content.ContentValues
import android.content.Context
import android.database.CharArrayBuffer
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(var mContext : Context?) : SQLiteOpenHelper(mContext,"insideDB",null,1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS User (user_id INT PRIMARY KEY,user_name VARCHAR(10),mail VARCHAR(20),group_id VARCHAR(10));"
        )

        db.execSQL(
            "CREATE TABLE IF NOT EXISTS Data (date INT PRIMARY KEY,title VARCHAR(20),path_name VARCHAR(100),text VARCHAR(140),data_type INT);"
        )

        /* 改行入れるとダメっぽい */
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class DBController(mContext: Context) {
    var db: SQLiteDatabase
    var DB : Database

    //var result: CharArrayBuffer?

    init {
        //result = null

        DB = Database(mContext)
        db = DB.writableDatabase    //DB作成
    }

    fun insertUserRecord(user_id: Int, user_name: String, mail: String, group_id: String) {
        db.execSQL(
            "INSERT INTO User VALUES(" + user_id + "," + "'" + user_name + "','" + mail + "','" + group_id + "');"
        )
    }

    fun insertDataRecord(date: Int, title: String,path_name: String, text: String, data_type: Int) {
        db.execSQL(
            "INSERT INTO Data VALUES(" + date + "," + "'" + title + "','" + path_name + "','" + text + "'," + data_type + ");"
        )
    }
}
//    fun getData() : RetArray{
//        var retDate : String = ""
//        var retTitle : String = ""
//        var retText : String = ""
//        var retDataType : String = ""
//
//        var data_type0 = 0
//        var data_type1 = 1
//
//        var sqlText = "SELECT * FROM Data WHERE data_type = ? or ?"
//
//        val c : Cursor = db.rawQuery(
//            sqlText,    //一度SQL文実行して
//            arrayOf(data_type0.toString(),data_type1.toString())    // data_type = 0 or 1で検索
//            /* これで全部吐き出す...ハズ */
//        )
//
//        if(c.moveToNext()){     //次の行ある？
//            retDate = c.getString(c.getColumnIndex("date"))     // c.getColumnIndex()内の列を代入
//            retTitle = c.getString(c.getColumnIndex("title"))
//            retText = c.getString(c.getColumnIndex("text"))
//            retDataType = c.getString(c.getColumnIndex("data_type"))
//        }
//
//        c.close()   //クローズ大事
//
//        var result = RetArray(retDate,retTitle,retText,retDataType)
//
//        return result
//    }
//}
//
//data class RetArray(
//    var date : String,
//    var title : String,
//    var text : String,
//    var datatype : String
//)
