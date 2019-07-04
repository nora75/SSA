package com.example.ssa

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {
    lateinit var dbc : DBController
    override fun onCreate(savedInstanceState: Bundle?) {
        dbc = DBController(this)
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            Database(this).writableDatabase

            dbc.insertUserRecord(1,  "aaa","aaa@hoge.com","abc")
            dbc.insertUserRecord(2,  "bbb","bbb@hoge.com","def")
            dbc.insertUserRecord(3,  "ccc","ccc@hoge.com","abc")

            dbc.insertDataRecord(1,"aaa","hoge.png","hoge",0)
            dbc.insertDataRecord(2,"bbb","piyo.png","piyo",1)
            dbc.insertDataRecord(3,"ccc","fuga.png","fuga",1)

        }catch(err : SQLiteException){
            textView.setText(err.toString())
        }
    }
}