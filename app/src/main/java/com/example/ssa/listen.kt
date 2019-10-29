package com.example.ssa

import android.content.Intent
import android.graphics.Color
import android.media.AudioManager
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_talk.*

class listen : AppCompatActivity() {

    private var flag: Boolean = false // True : 再生中, False : 再生されていない
    private var mediaPlayer: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listen)

        // look画面への移動
        to_look.setOnClickListener {
            val look = Intent(this, Look::class.java)
            startActivity(look)
        }

        // write画面への移動
        to_write.setOnClickListener {
            val write = Intent(this, write::class.java)
            startActivity(write)
        }

        // 聞くボタン押下時にtoggleTalk関数の呼び出し
        listen_button.setOnClickListener {
            toggleListen()
        }


    }

    // flagを元に、再生の開始、または終了をする
    private fun toggleListen() {
        flag = if (flag) {
            try {
                audioPause()
            } catch (e: Exception){ } // エラーが起きるかもなのでそれをキャッチするように。
            false
        } else {
            try {
                audioPlay()
            } catch (e: Exception){ } // エラーが起きるかもなのでそれをキャッチするように。
            true
        }
    }

    private fun audioSetup(path: Int): Boolean {

        var fileFlag = true
        try {
            mediaPlayer = MediaPlayer.create(this, path)

            // 音量調整を端末のボタンに任せる
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
        } catch (e: Exception) {
            fileFlag = false
        }

        return fileFlag

    }

    private fun audioPlay() {

        if (mediaPlayer == null) {
            if (!audioSetup(R.raw.music)) {
                return
            }
        }

        listen_button.setBackgroundColor(Color.RED)
        alert_listen.visibility = View.VISIBLE

        // 再生する
        mediaPlayer!!.start()

        // 終了を検知するリスナー
        mediaPlayer!!.setOnCompletionListener {
            audioStop()
        }
    }

    private fun audioPause() {
        displayAlertOff()
        // 再生終了
        mediaPlayer!!.pause()
    }

    private fun audioStop() {
        displayAlertOff()
        // 再生終了
        mediaPlayer!!.stop()
        // リセット
        mediaPlayer!!.reset()
        // リソースの解放
        mediaPlayer!!.release()

        mediaPlayer = null
    }

    private fun displayAlertOff() {
        listen_button.setBackgroundResource(android.R.drawable.btn_default)
        alert_listen.visibility = View.INVISIBLE
    }
}
