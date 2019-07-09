package com.example.ssa

import android.database.sqlite.SQLiteException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var dbc : DBController
    lateinit var capUser : RetUserArray
    lateinit var capData : RetDataArray

    override fun onCreate(savedInstanceState: Bundle?) {
        dbc = DBController(this)

        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            Database(this).writableDatabase //DB作成

//            dbc.insertUserRecord(1,"aaa","aaa@hoge.com","abc")
//            dbc.updateGroupId("hoge")
//            dbc.updateMail("hoge@hoge.com")
//
//            dbc.insertDataRecord(1,19990909,"aaa","Flower.png","flower is pritty",1)
//            dbc.insertDataRecord(2,20000101,"bbb","Bird.png","fried chickin",1)
//            dbc.insertDataRecord(3,20200220,"ccc","Moon.png","Grapefruit Moon",1)
//            dbc.insertDataRecord(4,20111111,null,"Voice1.png",null,0)
//            dbc.insertDataRecord(5,20121212,null,"Voice2.png",null,0)
//            dbc.insertDataRecord(6,20130303,null,"Voice3.png",null,0)
//
//            capUser = dbc.getUser()    //検索するid
//            var(user_id,user_name,mail,group_id) = capUser
//
//            capData = dbc.getDiary(2)    //検索する行
//            var(title,path,text) = capData  //戻り値を分解宣言
//
//            var audioPath = dbc.getAudio(1)
//
//            var dataArray : Array<RetDataArray> = dbc.dataList()
//
//            var(list1,list2,list3) = dataArray
//
//            var(title1,path1,text1) = list1
//            textView2.setText(title1 + "|" + path1 + "|" + text1)
//
//            var(title2,path2,text2) = list2
//            textView.setText(title2 + "|" + path2 + "|" + text2)
//
//            var(title3,path3,text3) = list3
//            textView4.setText(title3 + "|" + path3 + "|" + text3)
//
//            textView.setText(title + "|" + path + "|" + text) // 受け取ったデータ表示
//            textView2.setText(user_id + "|" + user_name + "|" + mail + "|" + group_id)
//            textView4.setText(audioPath)

        }catch(err : SQLiteException){
            textView.setText(err.toString())
        }
    }
}