package com.example.ssa

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
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
import com.github.kittinunf.fuel.core.requests.download
import com.github.kittinunf.fuel.httpDownload
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.synthetic.main.activity_look.*
import kotlinx.android.synthetic.main.my_text_view.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import kotlin.collections.List
import android.graphics.BitmapFactory
import android.support.v4.app.SupportActivity
import android.support.v4.app.SupportActivity.ExtraData
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T

class Look : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_look)
        //リストにデータを入れる
        val test = List(names.size) { i -> ProtoTypeData(names[i], viewtitle[i], date[i], data_type[i]) }
        //アダプターをせいせいし、viewにセットする
        val adapter = SampleListAdapter(this, test)
        myListView.adapter = adapter
        val json = returnDataList()
        Log.d("[ssa]", json.toString())
        //viewクリックの時のリスナ
        myListView.setOnItemClickListener { adapterView, view, postion, id ->
            val title = view.findViewById<TextView>(R.id.text2).text
            var content = ""
            val prams = listOf(
                "data_name" to dataNameList[postion],
                "user_id" to sh_user_id(),
                "password" to sh_pass_id()
            )
            //リクエストをここに書く
            val data_user_id = UserID[postion]
            //メソッドに変更予定がんば
            "http://34.83.80.2:50113/group/${sh_group_id()}/$data_user_id"
                .httpGet(prams)
                .download()
                .fileDestination{response, request ->
                    File.createTempFile("user_data_file",".txt")
                }
                .response { request, responce, result ->
                    when (result) {
                        is Result.Failure -> {
                            Log.d("リクエストエラー",result.getException().toString())
                            Toast.makeText(this,"リクエストエラー",Toast.LENGTH_LONG).show()
                        }
                        is Result.Success ->{
                            Log.d("result_result",result.toString())
                            Log.d("response_response",responce.toString())
                            val cashname = applicationContext.cacheDir.listFiles()
                            val fileName = cashname[cashname.size-1].name
                            val str = readFiles(fileName)
                            if (str != null) {
                                content = str
                            } else {
                                content = "取得失敗"
                            }
                            Log.d("リクエスト成功","リクエスト成功")
                        }
                    }
                }
            val prams2 = listOf(
                "data_name" to ImageName[postion],
                "user_id" to sh_user_id(),
                "password" to sh_pass_id()
            )
                //写真取得
                "http://34.83.80.2:50113/group/${sh_group_id()}/$data_user_id"
                    .httpGet(prams2)
                    .download()
                    .fileDestination { response, request ->
                        File.createTempFile("pict", ".png", filesDir)
                    }
                    .response { request, responce, result ->
                        when (result) {
                            is Result.Failure -> {
                                Log.d("リクエストエラー", result.getException().toString())
                                Toast.makeText(this, "リクエストエラー", Toast.LENGTH_LONG).show()
                            }
                            is Result.Success -> {
                                val cashname = applicationContext.filesDir.listFiles()
                                val fileName = cashname[cashname.size - 1].name
                                //上まであってる
                                Log.d("FileNameOut", fileName.toString())
                                val File = File(applicationContext.filesDir,fileName)
                                val FIlebyte = File.readBytes()

                                //画面遷移する時にデータを渡す
                                val See = Intent(this,SeeDainay::class.java)
                                See.putExtra("title","$title")
                                See.putExtra("Data","$content")
                                See.putExtra("Image",FIlebyte)
                                startActivity(See)
                            }
                        }
                    }
        }
//更新ボタンクリックのリスナ
        renewButton.setOnClickListener {
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
                                    //UserID,UserName,GroupID,DataName,ImageName,Title,DataType
                                    viewListID.add(viewListID.size)
                                    UserID.add(arraylist.UserID)
                                    names.add(arraylist?.UserName!!)
                                    GroupID.add(arraylist?.GroupID)
                                    dataNameList.add(arraylist.DataName)
                                    ImageName.add(arraylist.ImageName)
                                    date.add("11月2日")
                                    viewtitle.add(arraylist?.Title)
                                    data_type.add(arraylist?.DataType?.toInt())
                                }
                            }
                        }
                        val test = List(names.size) { i -> ProtoTypeData(names[i], viewtitle[i], date[i], data_type[i]) }
                        //アダプターを生成、viewにセットする
                        val adapter = SampleListAdapter(this, test)
                        myListView.adapter = adapter
                        Toast.makeText(this, "成功しました", Toast.LENGTH_LONG).show()
                    }
                }
            }
        return json
    }
    //falseなら該当するものがある、trueならないから標示
    fun dataNameCheck(dataname:String): Boolean{
        for (i in 0..(dataNameList.size-1)){
            if(dataNameList[i].equals(dataname)){
                return false
            }
        }
        return true
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
    fun readFiles(file: String): String? {

        // to check whether file exists or not
        val readFile = File(applicationContext.cacheDir, file)

        if(!readFile.exists()){
            Log.d("debug","No file exists")
            return null
        }
        else{
            return readFile.bufferedReader().use(BufferedReader::readText)
        }
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
        val holder: SampleViewHolder

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
        val sample = getItem(position) as ProtoTypeData
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