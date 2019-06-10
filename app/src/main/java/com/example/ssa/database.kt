package com.example.ssa

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(var mContext : Context?) : SQLiteOpenHelper(mContext,"sampleDB",null,1) {
    override fun onCreate(db: SQLiteDatabase) {
        var user_id = null
        var group_id = null
        var flag = false

        db.execSQL(
            "CREATE TABLE User ("
                    + user_id + " INT PRIMARY kEY,"
                    + group_id + " INT,"
                    + flag + " Boolean);"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class Add(mContext: Context?){
    private val db: SQLiteDatabase
    private val DBH: DBHelper

    init {
        DBH = DBHelper(mContext)  // DB生成
        db = DBH.getWritableDatabase()
    }

    fun addRecord(user_id : Int, group_id : Int, flag : Boolean){
        val values = ContentValues()

        values.put("user_id",user_id)
        values.put("groupt_id",group_id)
        values.put("flag",flag)
    }
}