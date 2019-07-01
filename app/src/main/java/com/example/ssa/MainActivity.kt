package com.example.ssa

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val db: SQLiteDatabase? = Database(this).writableDatabase
            // [つまりどころ]MainActivityがnullらしくてぬるぽ。SQLDatabase?にしてもぬるぽ
            val dbc: DBController? = null

            dbc?.insertUserRecord(0, "aaa", "AAA@hoge.com", "aaa")
            dbc?.insertUserRecord(1, "bbb", "BBB@hoge.com", "bbb")
            dbc?.insertUserRecord(2, "ccc", "CCC@hoge.com", "aaa")
        }catch(err : SQLiteException){
            textView.setText(err.toString())
        }
    }
}

//data class PickData(
//    var date : String,
//    var title : String,
//    var text : String,
//    var datatype : String
//)