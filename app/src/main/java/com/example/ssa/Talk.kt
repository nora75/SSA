package com.example.ssa

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.util.Log
import android.view.View
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FileDataPart
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_talk.*
import kotlinx.android.synthetic.main.activity_write.*
import permissions.dispatcher.*
import java.io.File

@RuntimePermissions
class Talk : AppCompatActivity() {

    private var flag: Boolean = false // True : 録音中, False : 録音されていない
    private val rec = Record() // Record 録音をするクラス
    private var recordPermission: Boolean = false // 権限のチェックに使用 True : 権限あり, False : なし

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState )
        this.title = resources.getString(R.string.app_name)
        setContentView(R.layout.activity_talk)

        // 録音ボタン押下時にtoggleTalk関数の呼び出し
        listen_button.setOnClickListener {
            toggleTalk()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        AlertDialog.Builder(this)
            .setTitle("話す画面")
            .setMessage("音声チャットを投稿する画面です。" +
                    "中央のボタンをタップすると録音開始し、" +
                    "もう一度タップすると録音終了をして" +
                    "投稿されます。")
            .show()

        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // 自動生成された関数にパーミッション・リクエストの結果に応じた処理の呼び出しを委譲
        onRequestPermissionsResult(requestCode, permissions ,grantResults)
    }

    // flagを元に、録音の開始、または終了をする
    private fun toggleTalk() {
        flag = if (flag) {
            if (recordPermission) {
                try {
                    stopTalk()
                    Log.d("owari","rokuonsyuuryou")
                    recordSend()
                } catch (e: Exception){ } // 録音が開始されていない時にエラーが起きるのでそれをキャッチするように。
            }
            false
        } else {
            recordPermission = true
            recordTalkWithPermissionCheck()
            true
        }
    }

    @NeedsPermission(Manifest.permission.RECORD_AUDIO)
    fun recordTalk() {
        if (recordPermission) {
            listen_button.setBackgroundResource(R.drawable.red_radius)
            alert_listen.visibility = View.VISIBLE
            rec.startRecording(applicationContext.filesDir)
            val a = applicationContext.filesDir.toString()
            Log.d("FilesDir",a)
        }
    }

    private fun stopTalk() {
        listen_button.setBackgroundResource(R.drawable.orange_radius)
        alert_listen.visibility = View.INVISIBLE
        rec.stopRecording()
    }

    @OnPermissionDenied(Manifest.permission.RECORD_AUDIO)
    fun onContactsDenied() {
        recordPermission = false
        flag = false
    }

    @OnNeverAskAgain(Manifest.permission.RECORD_AUDIO)
    fun onContactsNeverAskAgain() {
        recordPermission = false
        flag = false
    }

/*
*
*
*
*
 */
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
/*



 */
fun recordSend(){
    Log.d("Send","Send method")
    //グループIｄを取得
    val groupID = sh_group_id()
    //Record file の取得
    val filepath = applicationContext.filesDir.path + "/Record"
    val files = File(filepath)
    val listfile = files.listFiles()
    val RecordFile = listfile[listfile.size-1]
    val FilePath = listfile[listfile.size-1].path
    Log.d("filecheck",RecordFile.isFile.toString())
    //Log.d("hello",cashname.toString())

    //パラメータに入れてる
    val info = listOf(
        "user_id" to sh_user_id(),
        "password" to sh_pass_id(),
        "data_name" to RecordFile.name,
        "data_type" to "0",
        "title" to "title",
        "image_name" to "")

    Log.d("fuel","fuel before")
    val f = Fuel.upload("http://34.83.80.2:50113/group/$groupID",parameters = info)
        .add(FileDataPart(File(FilePath),name = "data"))
    f.response{result ->
        when(result){
            is Result.Failure -> {
                // 失敗した場合
                Toast.makeText(this,"音声に失敗しました",Toast.LENGTH_LONG).show()
                val ex = result.getException()
                Log.d("error msg", ex.toString())
            }
            is Result.Success -> {
                // 成功した場合。
                val ex = result.get()
                Toast.makeText(this,"音声保存に成功しました",Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    /*
    val f = Fuel.upload("http://34.83.80.2:50113/group/$groupID",parameters = info)
        .add(FileDataPart(RecordFile),name = "Data")
        f.response { result ->
            when(result){
                is Result.Failure ->{
                    //失敗した場合。
                    Toast.makeText(this,"失敗しました", Toast.LENGTH_LONG).show()
                    val ex = result.getException()
                    Log.d("error msg", ex.toString())
                }

                is Result.Success ->{
                    // 成功した場合。
                    Toast.makeText(this,"保存に成功しました",Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }*/
    }

}
