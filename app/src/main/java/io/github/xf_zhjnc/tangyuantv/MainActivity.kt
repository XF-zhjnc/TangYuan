package io.github.xf_zhjnc.tangyuantv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.xf_zhjnc.tangyuantv.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.btnCCTV.setOnClickListener {
            VideoPlayerActivity.start(this, "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8")
        }

        mBinding.btnGuangDong.setOnClickListener {
            VideoPlayerActivity.start(this, "http://ivi.bupt.edu.cn/hls/gdhd.m3u8")
        }
    }
}