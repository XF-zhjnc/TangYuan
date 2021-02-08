package io.github.xf_zhjnc.tangyuantv.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.github.xf_zhjnc.tangyuantv.VideoPlayerActivity
import io.github.xf_zhjnc.tangyuantv.databinding.ActivityLiveDetailBinding

class LiveDetailActivity : AppCompatActivity() {

    private lateinit var mViewModel: LiveDetailViewModel
    private lateinit var mBinding: ActivityLiveDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLiveDetailBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mViewModel = ViewModelProvider(this).get(LiveDetailViewModel::class.java)

        val channelId = intent.getStringExtra(CHANNEL_ID)?:"0"
        val contId = intent.getStringExtra(CONT_ID)?:"0"
        val dataType = intent.getStringExtra(DATA_TYPE)?:"0"

        mViewModel.liveDetailResult.observe(this, Observer {

            val videoUrl = "http://jsll.cctv4g.com/cctv1/${it.videoOriginalId}.m3u8?t=${System.currentTimeMillis()}"
            //val videoUrl = "http://jsll.cctv4g.com/cctv1/9000000000202102081016202102081105.m3u8?t=1612760788&k=6ea0e7c666279479&m=790f9af3c3b386bb95df3edf778234ca"
            mBinding.btnPlay.setOnClickListener {
                VideoPlayerActivity.start(this, videoUrl)
            }
        })

        mViewModel.requestData(channelId, contId, dataType)
    }

    companion object {
        const val CHANNEL_ID = "channelId"
        const val CONT_ID = "contId"
        const val DATA_TYPE = "dataType"

        fun start(context: Context, channelId: String, contId: String, dataType: String) {
            val intent = Intent(context, LiveDetailActivity::class.java)
            intent.putExtra(CHANNEL_ID, channelId)
            intent.putExtra(CONT_ID, contId)
            intent.putExtra(DATA_TYPE, dataType)
            context.startActivity(intent)
        }
    }

}