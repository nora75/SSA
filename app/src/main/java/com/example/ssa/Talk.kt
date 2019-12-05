package com.example.ssa

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_talk.*
import permissions.dispatcher.*

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
            listen_button.setBackgroundResource(R.drawable.red_radius)
            alert_listen.visibility = View.VISIBLE
            rec.startRecording(applicationContext.filesDir)
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

}
