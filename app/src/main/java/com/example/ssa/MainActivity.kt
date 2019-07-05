package com.example.ssa

import android.database.CharArrayBuffer
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {
    lateinit var dbc : DBController
    lateinit var capData : RetArray

    override fun onCreate(savedInstanceState: Bundle?) {
        dbc = DBController(this)

        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            Database(this).writableDatabase //DB作成

//            dbc.insertUserRecord(1,  "aaa","aaa@hoge.com","abc")
//            dbc.insertUserRecord(2,  "bbb","bbb@hoge.com","def")
//            dbc.insertUserRecord(3,  "ccc","ccc@hoge.com","abc")

//            dbc.insertDataRecord(1,19990909,"aaa","hoge.png","hoge",0)
//            dbc.insertDataRecord(2,20000101,"bbb","piyo.png","piyo",1)
//            dbc.insertDataRecord(3,20200220,"ccc","fuga.png","fuga",1)

            capData = dbc.getData(2)    //検索するid
            var(title,path,text) = capData  //戻り値を分解宣言

            textView.setText(title) // 受け取ったデータ表示
            textView2.setText(path)
            textView3.setText(text)

        }catch(err : SQLiteException){
            textView4.setText(err.toString())
        }
    }
}