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
            "CREATE TABLE IF NOT EXISTS Data (diary_id INT PRIMARY KEY,date INT,title VARCHAR(20),path_name VARCHAR(100),text VARCHAR(140),data_type INT);"
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

    lateinit var result: RetArray

    init {
        DB = Database(mContext)
        db = DB.writableDatabase    //DB作成
    }

    fun insertUserRecord(user_id: Int, user_name: String, mail: String, group_id: String) {
        db.execSQL(
            "INSERT INTO User VALUES(" + user_id + "," + "'" + user_name + "','" + mail + "','" + group_id + "');"
        )
    }

    fun updateUserRecord(user_id: Int,group_id: String){
        db.execSQL(
            "UPDATE User SET user_id = " + group_id + " WHERE user_id = '" + user_id + "';"
        )
    }

    fun insertDataRecord(diary_id: Int,date: Int, title: String,path_name: String, text: String, data_type: Int) {
        db.execSQL(
            "INSERT INTO Data VALUES(" + diary_id + "," + date + "," + "'" + title + "','" + path_name + "','" + text + "'," + data_type + ");"
        )
    }

    fun getData(diary_id: Int) : RetArray{
        var retTitle = ""
        var retPath =  ""
        var retText = ""

        var sqlText = "SELECT * FROM Data WHERE diary_id = ?;"  // ?のところに引数

        val c : Cursor = db.rawQuery(
            sqlText,    //一度SQL文実行して
            arrayOf(diary_id.toString())    // id指定
        )

        retTitle = c.getString(c.getColumnIndex("title"))     // c.getColumnIndex()内の列を代入
        retPath = c.getString(c.getColumnIndex("path_name"))
        retText = c.getString(c.getColumnIndex("text"))

        c.close()   //クローズ大事

        return RetArray(retTitle,retPath,retText)  //一行しかreturnできない
    }
}

data class RetArray(
    var date : String,
    var title : String,
    var text : String
)
