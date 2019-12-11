package com.example.ssa

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.Toast
import com.github.kittinunf.fuel.httpPost
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.synthetic.main.activity_register.*
import java.nio.charset.Charset
import javax.xml.transform.Result

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
class add_group:AppCompatActivity() {

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

    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    //header:ヘッダー
    val header: HashMap<String, String> = hashMapOf("Content-Type" to "application/json")
    //url:基本的なURL
    val requestAdapter = moshi.adapter(ChangeGroup::class.java)

    val change = ChangeGroup(
        user_name = "chinchin"
    )

    fun Change_group_id(user_id: Int) {
        "http://34.83.80.2:50112/users/$user_id"
            .httpPost()
            .header()
            .body(requestAdapter.toJson(change), Charset.defaultCharset())
            .responseString { request, response, result ->
                when (result) {
                    is com.github.kittinunf.result.Result.Failure -> {
                        val ex = result.getException()
                        Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show()
                    }
                    is com.github.kittinunf.result.Result.Success -> {
                        val data = result.get()
                        Toast.makeText(this, "正常", Toast.LENGTH_LONG).show()
                    }
                }
            }
    }
}


