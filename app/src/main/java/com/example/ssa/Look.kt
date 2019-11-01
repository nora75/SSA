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
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.synthetic.main.activity_look.*
import kotlinx.android.synthetic.main.my_text_view.view.*
import org.json.JSONArray
import org.json.JSONObject
import kotlin.collections.List

data class ProtoTypeData(
    val names: String,
    val title: String,
    val date: String,
    val data_type: Int
)

data class SampleViewHolder(
    val imageView: ImageView,
    val text1: TextView,
    val text2: TextView,
    val text3: TextView
)

class Look : AppCompatActivity() {
    //private val dataStore: SharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
    //private val GROUPID: String = dataStore.getString("GROUP_ID", "NULL")
    //private val USERID = dataStore.getInt("USER_ID", 1)
    //private val PASSWORD: String = dataStore.getString("Pass", "")
    //テストデータ UserID,UserName,GroupID,DataName,ImageName,Title,DataType
    private val names:MutableList<String> = mutableListOf(
        "おじいちゃん",
        "おばあちゃん",
        "ははおや",
        "ちちおや",
        "まご"
    )

    private val title:MutableList<String> = listOf(
        "おはなみ",
        "りょこう",
        "しごと",
        "やすみ",
        "しゅうがくりょこう"
    ).toMutableList()

    private val date:MutableList<String> = listOf(
        "9/2",
        "9/3",
        "9/4",
        "9/5",
        "9/6"
    ).toMutableList()

    private val data_type:MutableList<Int> = listOf(
        0, 1, 0, 1, 1
    ).toMutableList()

    private val dataNameList:MutableList<String> = mutableListOf(
        "sample1",
        "sample2",
        "sample3",
        "sample4",
        "sample5"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_look)

//リストにデータを入れる
        val test = List(names.size) { i -> ProtoTypeData(names[i], title[i], date[i], data_type[i]) }
//アダプターをせいせいし、viewにセットする
        val adapter = SampleListAdapter(this, test)
        myListView.adapter = adapter
        val json = returnDataList()
        Log.d("[ssa]", json.toString())

//viewクリックの時のリスナ
        myListView.setOnItemClickListener { adapterView, view, postion, id ->
            val name = view.findViewById<TextView>(R.id.text1).text
            Toast.makeText(this, "$name", Toast.LENGTH_LONG).show()
            //リクエストをここに書く
            //"http://34.83.80.2:50113/group/${sh_group_id()}/${sh_user_id()}"
            //画面を遷移させパス等を送る
            /*
            選択されたdata_typeにより、画面を遷移する
             */
            /*if (){
            // getData()

            }else{

            }

*/
        }
//更新ボタンクリックのリスナ
        renewButton.setOnClickListener {
            // val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            // val header: HashMap<String, String> = hashMapOf("Content-Type" to "application/json")
            // val requestAdapter = moshi.adapter(GetDataListResponse::class.java)
            val json = returnDataList()
            Log.d("[ssa]", json.toString())
        }

    }

    fun returnDataList(): JSONArray? {

        val GROUPID = sh_group_id()
        val USERID = sh_user_id()
        val PASSWORD = sh_pass_id()
        var json: JSONArray? = null
        val parms = listOf(
            "user_id" to USERID,
            "password" to PASSWORD
        )

        "http://34.83.80.2:50113/group/${GROUPID}/"
            .httpGet(parms)
            .responseJson { request, responce, result ->
                when (result) {
                    is Result.Failure -> {
                        val ex = result.getException()
                        Toast.makeText(this, "失敗しました", Toast.LENGTH_LONG).show()
                        Log.d("error_msg", ex.toString())
                    }
                    is Result.Success -> {
//                        val data = result.get()
                        json = result.value.array()
                        for (i in 0..(json!!.length()-1)){
                            val data1 = json!![i] as JSONObject
                            Log.d("[ssa]", data1.toString()) // 高知県
                            Log.d("[ssa]", json.toString()) // 高知県
                            //パーサー
                            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                            val requestAdapter = moshi.adapter(GetDataListResponse::class.java)
                            val arraylist = requestAdapter.fromJson(data1.toString())

                            if (arraylist != null) {
                                if(dataNameCheck(arraylist.DataName)) {
                                    names.add(arraylist?.UserName!!)
                                    title.add(arraylist?.Title)
                                    date.add("11月2日")
                                    data_type.add(arraylist?.DataType?.toInt())
                                    dataNameList.add(arraylist.DataName)
                                }
                            }
                        }
                        val test = List(names.size) { i -> ProtoTypeData(names[i], title[i], date[i], data_type[i]) }
//アダプターをせいせいし、viewにセットする
                        val adapter = SampleListAdapter(this, test)
                        myListView.adapter = adapter

                        Toast.makeText(this, "成功しました", Toast.LENGTH_LONG).show()
                    }
                }
            }
        return json

    }
    /*
    fun viewListSet(result: Result){
        val json = result.value.array()
        val data1 = json!![0] as JSONObject
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val requestAdapter = moshi.adapter(GetDataListResponse::class.java)
        val arraylist = requestAdapter.fromJson(data1.toString())
        Log.d("aaa",arraylist?.UserName)
        arraylist?.Title
        arraylist?.DataType
    }
     */
    //falseなら該当するものがある、trueならないから標示
    fun dataNameCheck(dataname:String): Boolean{
        for (i in 0..(dataNameList.size-1)){
            if(dataNameList[i].equals(dataname)){
                return false
            }
        }
        return true
    }


    fun getData() {
        //Toast.makeText(this,"トースト表示成功",Toast.LENGTH_LONG).show()

        val GROUPID = sh_group_id()
        val USERID = sh_user_id()
        Fuel.get("http://34.83.80.2:50113/group/${GROUPID}/${USERID}")
            .responseJson { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        val ex = result.getException()
                        Toast.makeText(this, "失敗しました", Toast.LENGTH_LONG).show()
                        Log.d("error_msg", ex.toString())
                    }
                    is Result.Success -> {
                        val data = result.get()
                        Log.d("成功データ取得", data.toString())
                        Toast.makeText(this, "成功しました", Toast.LENGTH_LONG).show()
                    }
                }
            }
    }

    private fun sh_user_id():Int{
        val dataStore: SharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val user_id = dataStore.getInt("USER_ID",1)
        return user_id
    }

    private fun sh_group_id():String{
        val dataStore: SharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val group_id = dataStore.getString("GROUP_ID","")
        return group_id
    }

    private fun sh_pass_id():String{
        val dataStore: SharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val pass = dataStore.getString("Pass","")
        return pass
    }

}

class SampleListAdapter(
    context: Context,
    sample: List<ProtoTypeData>
) : ArrayAdapter<ProtoTypeData>(context, 0, sample) {
    private val layoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view = convertView
        var holder: SampleViewHolder

        if (view == null) {
            view = layoutInflater.inflate(R.layout.my_text_view, parent, false)
            holder = SampleViewHolder(
                view.imageView,
                view.text1,
                view.text2,
                view.text3
            )
            view.tag = holder
        } else {
            holder = view.tag as SampleViewHolder
        }
        var sample = getItem(position) as ProtoTypeData
        holder.text1.text = sample.names
        holder.text2.text = sample.title
        holder.text3.text = sample.date
        if (sample.data_type == 1) {
            holder.imageView.setImageResource(R.drawable.tabi_camera_nikki)
        } else {
            holder.imageView.setImageResource(R.drawable.player_button_blue01_saisei)
        }
        return view
    }

}