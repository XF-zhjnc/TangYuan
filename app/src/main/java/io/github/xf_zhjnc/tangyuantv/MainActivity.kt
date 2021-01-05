package io.github.xf_zhjnc.tangyuantv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCCTV.setOnClickListener {
            VideoPlayerActivity.start(this, "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8")
        }

        btnGuangDong.setOnClickListener {
            VideoPlayerActivity.start(this, "http://ivi.bupt.edu.cn/hls/gdhd.m3u8")
        }
    }
}