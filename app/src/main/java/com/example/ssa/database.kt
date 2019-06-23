package com.example.ssa

import android.content.ContentValues
import android.content.Context
import android.database.CharArrayBuffer
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

abstract class DBController(var mContext : Context?) : SQLiteOpenHelper(mContext,"insideDB",null,1) {
    fun createTable(db: SQLiteDatabase) {
        val sb = StringBuilder()
        var sqlText : String

        sb.append("CREATE TABLE User (")
        sb.append("user_id INT PRIMARY kEY,")
        sb.append("group_id INT," )
        sb.append("flag Boolean")
        sb.append(");")
        sqlText = sb.toString()
        db.execSQL(sqlText)     //Userテーブルつくる

        sb.append("CREATE TABLE Talk(")
        sb.append("group_id TEXT")
        sb.append("user_name TEXT")
        sb.append("date DATE")
        sb.append("audio_name text")
        sb.append(");")
        sqlText = sb.toString()
        db.execSQL(sqlText)     //Talkテーブルつくる

        sb.append("CREATE TABLE Diary(")
        sb.append("group_id TEXT")
        sb.append("user_name TEXT")
        sb.append("date DATE")
        sb.append("title TEXT")
        sb.append("img_name TEXT")
        sb.append("text TEXT")
        sb.append(");")
        sqlText = sb.toString()
        db.execSQL(sqlText)     //Diaryテーブルつくる
    }

    fun insertUserRecord(user_id : Int, user_name : String, mail : String, group_id : String,db: SQLiteDatabase){
        val values = ContentValues()

        values.put("user_id",user_id)
        values.put("user_name",user_name)
        values.put("mail",mail)
        values.put("group_id",group_id)

        db.insert("User",null,values)
    }

    fun insertTalkRecord(date : Int, audio_name : String, data_type : Int,db: SQLiteDatabase){
        val values = ContentValues()

        values.put("date",date)     //Date型対応してねぇ！
        values.put("audio_name",audio_name)
        values.put("data_type",data_type)

        db.insert("Talk",null,values)
    }

    fun insertDiaryRecord(date : Int, title : String, img_name : String, text : String, data_type : Int,db: SQLiteDatabase){
        val values = ContentValues()

        values.put("date",date)
        values.put("title",title)
        values.put("img_name",img_name)
        values.put("text",text)
        values.put("data_type",data_type)

        db.insert("Diary",null,values)
    }
}

class DataList(){
    var result : CharArrayBuffer?

    init{
        result = null
    }

    fun dataList(db : SQLiteDatabase) : CharArrayBuffer?{
        var sqlText = "SELECT * FROM Talk INNER JOIN Diary"

        val c : Cursor = db.rawQuery(sqlText , null)

        c.moveToFirst()     //カーソル初期化

        var i = 0
        while(true) {
            while (i < 6) {
                c.copyStringToBuffer(i, result)

                i++     //列移動
            }

            if (c.isLast == true) {     //最終行まで見た？
                break
            }else{
                c.moveToNext()       //行移動

                i = 0
            }
        }
        return result
    }
}

