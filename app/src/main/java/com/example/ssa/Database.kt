package com.example.ssa

import android.content.ContentValues
import android.content.Context
import android.database.CharArrayBuffer
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.renderscript.ScriptIntrinsicColorMatrix

class Database(var mContext : Context?) : SQLiteOpenHelper(mContext,"insideDB",null,1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS User (user_id INT PRIMARY KEY,user_name VARCHAR(10),mail VARCHAR(20),group_id VARCHAR(10));"
        )

        db.execSQL(
            "CREATE TABLE IF NOT EXISTS Data (data_id INT PRIMARY KEY,date INT,title VARCHAR(20),path_name VARCHAR(100),text VARCHAR(140),data_type INT);"
        )

        /* data_type = 音声:0,日記:1 */
        /* SQL文は改行入れるとダメっぽい */
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class DBController(mContext: Context) {
    var db: SQLiteDatabase
    var DB : Database

    init {
        DB = Database(mContext)
        db = DB.writableDatabase    //DB作成
    }

    fun insertUserRecord(user_id: Int, user_name: String, mail: String, group_id: String) {
        db.execSQL(
            "INSERT INTO User VALUES(" + user_id + "," + "'" + user_name + "','" + mail + "','" + group_id + "');"
        )
    }

    fun updateGroupId(group_id: String){
        db.execSQL(
            "UPDATE User SET group_id = '" + group_id + "';"
        )
    }

    fun updateMail(mail: String){
        db.execSQL(
            "UPDATE User SET mail = '" + mail + "';"
        )
    }

    fun insertDataRecord(data_id: Int,date: Int, title: String?,path_name: String, text: String?, data_type: Int) {
        db.execSQL(
            "INSERT INTO Data VALUES(" + data_id + "," + date + "," + "'" + title + "','" + path_name + "','" + text + "'," + data_type + ");"
        )
    }

    fun getUser() : RetUserArray{
        var retId  = ""
        var retName = ""
        var retMail = ""
        var retGroup = ""

        val sqlText = "SELECT * FROM User;"   // ?のところに引数

        val c : Cursor = db.rawQuery(
            sqlText,    //一度SQL文実行して
            null
        )

        c.moveToFirst()

        retId = c.getString(c.getColumnIndex("user_id"))     // c.getColumnIndex()内の列を代入
        retName = c.getString(c.getColumnIndex("user_name"))
        retMail = c.getString(c.getColumnIndex("mail"))
        retGroup = c.getString(c.getColumnIndex("group_id"))

        c.close()

        return RetUserArray(retId,retName,retMail,retGroup)   //一行しかreturnできない
    }

    fun getDiary(rowNum: Int) : RetDiaryArray{  //Userとやること同じ
        var retTitle = ""
        var retPath =  ""
        var retText = ""

        var sqlText = "SELECT * FROM Data WHERE data_type = 1;"

        val c : Cursor = db.rawQuery(
            sqlText,
            null
        )

        c.moveToPosition(rowNum)   //引数の数の絶対行指定

        retTitle = c.getString(c.getColumnIndex("title"))
        retPath = c.getString(c.getColumnIndex("path_name"))
        retText = c.getString(c.getColumnIndex("text"))

        c.close()   //クローズ大事

        return RetDiaryArray(retTitle,retPath,retText)
    }

    fun getAudio(rowNum: Int) : String{  //data_type = 0(音声ファイル)を取り出し
        var retPath =  ""

        var sqlText = "SELECT * FROM Data WHERE data_type = 0;"

        val c : Cursor = db.rawQuery(
            sqlText,
            null
        )

        c.moveToPosition(rowNum)

        retPath = c.getString(c.getColumnIndex("path_name"))

        c.close()

        return retPath
    }
}

data class RetDiaryArray(
    var title : String,
    var path : String,
    var text : String
)

data class RetUserArray(
    var user_id : String,
    var user_name : String,
    var mail : String,
    var group_id : String
)
