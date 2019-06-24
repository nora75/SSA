package com.example.ssa

import android.content.ContentValues
import android.content.Context
import android.database.CharArrayBuffer
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(var mContext : Context?) : SQLiteOpenHelper(mContext,"insideDB",null,1) {
    override fun onCreate(db: SQLiteDatabase) {
        val sb = StringBuilder()
        var sqlText : String

        sb.append("CREATE TABLE User (")
        sb.append("user_id INT PRIMARY kEY,")
        sb.append("group_id INT," )
        sb.append("flag Boolean")
        sb.append(");")
        sqlText = sb.toString()
        db.execSQL(sqlText)     //Userテーブルつくる

        sb.append("CREATE TABLE Data(")
        sb.append("group_id TEXT")
        sb.append("user_name TEXT")
        sb.append("date DATE")
        sb.append("title TEXT")
        sb.append("path_name TEXT")
        sb.append("text TEXT")
        sb.append(");")
        sqlText = sb.toString()
        db.execSQL(sqlText)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertUserRecord(user_id : Int, user_name : String, mail : String, group_id : String,db: SQLiteDatabase){
        val values = ContentValues()

        values.put("user_id",user_id)
        values.put("user_name",user_name)
        values.put("mail",mail)
        values.put("group_id",group_id)

        db.insert("User",null,values)
    }

    fun insertDataRecord(date : Int, title : String, path_name : String, text : String, data_type : Int,db: SQLiteDatabase){
        val values = ContentValues()

        values.put("date",date)
        values.put("title",title)
        values.put("path_name",path_name)
        values.put("text",text)
        values.put("data_type",data_type)

        db.insert("Diary",null,values)
    }
}

    /* data_type : 音声0,日記1 */

class DBController(mContext: Context){
    val db: SQLiteDatabase
    val DB : Database

    var result : CharArrayBuffer?

    init{
        result = null

        DB = Database(mContext)
        db = DB.writableDatabase    //DB作成!
    }

    fun getData(data_type0 : Int = 0,data_type1 : Int = 1) : CharArrayBuffer?{
        var sqlText = "SELECT * FROM Data"
        var i = 0

        val c : Cursor = db.rawQuery(
            sqlText,    //一度SQL文実行して
            arrayOf(data_type0.toString(),data_type1.toString())    //data_type0と1で検索
            /* これで全部吐き出す...ハズ */
        )

        c.moveToFirst() //1行目

        while(c.moveToNext()){
            c.copyStringToBuffer(i,result)  //i行目をresultにぶち込む

            i++
        }

        c.close()   //クローズ大事

        return result
    }
}

