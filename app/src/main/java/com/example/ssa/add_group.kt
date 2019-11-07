package com.example.ssa

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.kittinunf.fuel.httpPost

/*
*グループ変更機能
* 使用用途
* 間違えてGroup_idを入れずに登録してしまった場合に、入れ直す機能
 */

/*
* メソッド
* Change_group_id
*
*
 */
class add_group : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_group)

    }


    var Change = Change_group(
        group_id = "",
        password = ""
    )

    /**
     *
     */
    fun Change_group_id(user_id:Int){
        /*
        "http://34.83.80.2:50112/users/$user_id"
            .httpPost()
         */
    }
}
