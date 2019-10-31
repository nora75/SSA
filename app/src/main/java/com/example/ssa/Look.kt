package com.example.ssa

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.synthetic.main.activity_look.*
import kotlinx.android.synthetic.main.my_text_view.view.*
import java.nio.charset.Charset
import kotlin.reflect.jvm.internal.impl.util.ReturnsCheck

class Look : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_look)
//テストデータ
// UserID,UserName,GroupID,DataName,ImageName,Title,DataType
        val names = listOf(
            "おじいちゃん",
            "おばあちゃん",
            "ははおや",
            "ちちおや",
            "まご"
        )

        val title = listOf(
            "おはなみ",
            "りょこう",
            "しごと",
            "やすみ",
            "しゅうがくりょこう"
        )

        val date = listOf(
            "9/2",
            "9/3",
            "9/4",
            "9/5",
            "9/6"
        )

        val data_type = listOf(
            0,1,0,1,1
        )
//リストにデータを入れる
        val test = List(names.size){i ->ProttypeData(names[i],title[i],date[i],data_type[i])}
//アダプターをせいせいし、viewにセットする
        val adapter = SampleListAdapter(this,test)
        myListView.adapter = adapter

//viewクリックの時のリスナ
        myListView.setOnItemClickListener{adapterView,view,postion,id ->
            val name = view.findViewById<TextView>(R.id.text1).text
            Toast.makeText(this,"$name",Toast.LENGTH_LONG).show()
            //リクエストをここに書く
            //"http://34.83.80.2:8000/group/
            val res = Fuel.get("http://34.83.80.2:50113/group/${sh_group_id()}/${sh_user_id()}")
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            Toast.makeText(this, "失敗しました", Toast.LENGTH_LONG).show()
                            Log.d("error_msg", ex.toString())
                        }
                        is Result.Success -> {
                            val data = result.get()
                            Log.d("成功データ取得", data)
                            Toast.makeText(this, "成功しました", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            //画面を遷移させパス等を送る
            /*
            選択されたdata_typeにより、画面を遷移する
             */
            /*if (){

            }else{

            }

*/
        }
//更新ボタンクリックのリスナ
        renewButton.setOnClickListener {
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val header : HashMap<String, String> = hashMapOf("Content-Type" to "application/json")
            val requestAdapter = moshi.adapter(RenewList::class.java)

            val group_id = sh_group_id()
            val renew = RenewList(
                user_id = sh_user_id(),
                password = sh_pass_id()
            )

            val parms = listOf(
                "user_id" to sh_user_id(),
                "password" to sh_pass_id()
            )

            "http://34.83.80.2:50113/group/$group_id"
                .httpGet()
                .header(header)
                .body(requestAdapter.toJson(renew), Charset.defaultCharset())
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            Toast.makeText(this, "失敗しました", Toast.LENGTH_LONG).show()
                            Log.d("error_msg", ex.toString())
                        }
                        is Result.Success -> {
                            val data = result.get()
                            Log.d("成功データ取得", data)
                            Toast.makeText(this, "成功しました", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            //Toast.makeText(this,"トースト表示成功",Toast.LENGTH_LONG).show()
        }
    }
    //group_idの取得
    private fun sh_group_id():String{
        val dataStore: SharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val group_id = dataStore.getString("GROUP_ID","")
        return group_id
    }

    private fun sh_user_id():Int{
        val dataStore: SharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val user_id = dataStore.getInt("USER_ID",1)
        return user_id
    }

    private fun sh_pass_id():String{
        val dataStore: SharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val pass = dataStore.getString("Pass","")
        return pass
    }
}

data class ProttypeData(
    val names:String,
    val title:String,
    val date: String,
    val data_type:Int
)

data class SampleViewHolder(
    val imageView: ImageView,
    val text1: TextView,
    val text2: TextView,
    val text3: TextView
)

class SampleListAdapter(context: Context,sample:List<ProttypeData>) : ArrayAdapter<ProttypeData>(context,0,sample){
    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view = convertView
        var holder : SampleViewHolder

        if(view == null){
            view = layoutInflater.inflate(R.layout.my_text_view, parent ,false)
            holder = SampleViewHolder(
                view.imageView,
                view.text1,
                view.text2,
                view.text3
            )
            view.tag = holder
        }
        else
        {
            holder = view.tag as SampleViewHolder
        }
        var sample = getItem(position) as ProttypeData
        holder.text1.text = sample.names
        holder.text2.text = sample.title
        holder.text3.text = sample.date
        if(sample.data_type == 1){
            holder.imageView.setImageResource(R.drawable.tabi_camera_nikki)
        }
        else{
            holder.imageView.setImageResource(R.drawable.player_button_blue01_saisei)
        }
        return view
    }

}