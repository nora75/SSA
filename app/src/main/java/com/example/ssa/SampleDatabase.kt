package com.example.ssa

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.ContactsContract

class SampleDatabase (var mContext: Context?) : SQLiteOpenHelper(mContext, "Sample", null,1) {
    //  SQLiteOpenHelper
    // 第１引数 :
    // 第２引数 : データベースの名称
    // 第３引数 : null
    // 第４引数 : データベースのバージョン

    override fun onCreate(db: SQLiteDatabase?) {
        // テーブルがなかったときに が呼ばれる
        // execSQL で　クエリSQL文を実行 これでDBの構造が決定
        db?.execSQL(
            "CREATE TABLE Sample  ( " +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "type integer not null, "+
                    "date text not null, " +
                    "memo text not null " +
                    ");")
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // バージョンが変わった時に実行される
        db?.execSQL("DROP TABLE IF EXISTS Sample;")
        onCreate(db);
        // 今回は,一度消して、作り直ししてます　
    }
}