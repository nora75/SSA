package com.example.ssa

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_talk.*
import permissions.dispatcher.*

@RuntimePermissions
class Talk : AppCompatActivity() {

    private var flag: Boolean = false // True : 録音中, False : 録音されていない
    private val rec = Record() // Record 録音をするクラス
    private var recordPermission: Boolean = false // 権限のチェックに使用 True : 権限あり, False : なし

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.title = resources.getString(R.string.string_talk)
        setContentView(R.layout.activity_talk)

        // listen画面への移動
        to_listen.setOnClickListener {
            val listen = Intent(this, listen::class.java)
            startActivity(listen)
        }

        // write画面への移動
        to_write.setOnClickListener {
            val listen = Intent(this, listen::class.java)
            startActivity(listen)
        }

        // 録音ボタン押下時にtoggleTalk関数の呼び出し
        talk_button.setOnClickListener {
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
            talk_button.setBackgroundColor(Color.RED)
            alert_talk.visibility = View.VISIBLE
            rec.startRecording(applicationContext.filesDir)
        }
    }

    private fun stopTalk() {
        talk_button.setBackgroundResource(android.R.drawable.btn_default)
        alert_talk.visibility = View.INVISIBLE
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

    @OnShowRationale(Manifest.permission.RECORD_AUDIO)
    fun showRationaleForContacts(request: PermissionRequest){
    }

}
