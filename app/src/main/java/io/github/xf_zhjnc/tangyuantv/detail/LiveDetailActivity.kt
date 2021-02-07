package io.github.xf_zhjnc.tangyuantv.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import io.github.xf_zhjnc.tangyuantv.databinding.ActivityLiveDetailBinding

class LiveDetailActivity : AppCompatActivity() {

    private lateinit var mViewModel: LiveDetailViewModel
    private lateinit var mBinding: ActivityLiveDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLiveDetailBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mViewModel = ViewModelProvider(this).get(LiveDetailViewModel::class.java)

        mViewModel.requestData()
    }

    companion object {
        const val CHANNEL_ID = "channelId"

        fun start(context: Context, channelId: String) {
            val intent = Intent(context, LiveDetailActivity::class.java)
            intent.putExtra(CHANNEL_ID, channelId)
            context.startActivity(intent)
        }
    }

}