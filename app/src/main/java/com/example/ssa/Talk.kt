package com.example.ssa

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FileDataPart
import kotlinx.android.synthetic.main.activity_talk.*
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // 自動生成された関数にパーミッション・リクエストの結果に応じた処理の呼び出しを委譲
        onRequestPermissionsResult(requestCode, grantResults)
    }

    // flagを元に、録音の開始、または終了をする
    private fun toggleTalk() {
        flag = if (flag) {
            if (recordPermission) {
                try {
                    stopTalk()
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
            listen_button.setBackgroundResource(R.drawable.mic_icon_radius_red)
            alert_listen.visibility = View.VISIBLE
            rec.startRecording(applicationContext.filesDir)
        }
    }

    private fun stopTalk() {
        listen_button.setBackgroundResource(R.drawable.mic_icon_radius)
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
    //グループIｄを取得
    val groupID = sh_group_id()
    //val fileName = cashname[cashname.size - 1].name
    //Log.d("hello",cashname.toString())

    //パラメータに入れてる
    val info = listOf(
        "user_id" to sh_user_id(),
        "password" to sh_pass_id(),
        //"data_name" to "${textFile.name}",
        "data_type" to "0",
        "title" to "$title",
        "image_name" to null)

    }
}
