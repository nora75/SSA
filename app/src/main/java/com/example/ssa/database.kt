package com.example.ssa

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

abstract class DBHelper(var mContext : Context?) : SQLiteOpenHelper(mContext,"sampleDB",null,1) {
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
}

class Add(mContext: Context?){
    fun addUserRecord(user_id : Int, group_id : Int, flag : Boolean){
        val values = ContentValues()

        values.put("user_id",user_id)
        values.put("group_id",group_id)
        values.put("flag",flag)
    }

    fun addTalkRecord(group_id : String, user_name : String, date : Int, audio_name : String){
        val values = ContentValues()

        values.put("group_id",group_id)
        values.put("user_name",user_name)
        values.put("date",date)     //Date型対応してねぇ！
        values.put("audio_name",audio_name)
    }

    fun addDiaryRecord(group_id : String, user_name : String, date : Int, title : String, img_name : String, text : String){
        val values = ContentValues()

        values.put("group_id",group_id)
        values.put("user_name",user_name)
        values.put("date",date)
        values.put("title",title)
        values.put("img_name",img_name)
        values.put("text",text)
    }
}

