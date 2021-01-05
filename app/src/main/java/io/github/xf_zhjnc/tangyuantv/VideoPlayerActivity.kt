package io.github.xf_zhjnc.tangyuantv

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.github.xf_zhjnc.tangyuantv.databinding.ActivityVideoPlayerBinding
import tv.danmaku.ijk.media.player.IMediaPlayer

/**
 * NAME: 柚子啊
 * DATE: 2021-01-05
 * DESC: 视频播放页
 */
class VideoPlayerActivity : AppCompatActivity(), TYVideoListener {

    private var videoPath: String? = null

    private lateinit var mBinding: ActivityVideoPlayerBinding

    companion object {
        const val VIDEO_PATH = "video_path"

        fun start(context: Context, url: String) {
            val intent = Intent(context, VideoPlayerActivity::class.java)
            intent.putExtra(VIDEO_PATH, url);
            context.startActivity(intent)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_IMMERSIVE or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        videoPath = intent.getStringExtra(VIDEO_PATH)

        mBinding.videoPlayer.setTYVideoListener(this)
        videoPath?.let { mBinding.videoPlayer.setPath(it) }
        mBinding.videoPlayer.load()
    }

    override fun onBufferingUpdate(p0: IMediaPlayer?, p1: Int) {

    }

    override fun onCompletion(p0: IMediaPlayer?) {

    }

    override fun onPrepared(p0: IMediaPlayer?) {
        mBinding.videoPlayer.start()
    }

    override fun onInfo(p0: IMediaPlayer?, p1: Int, p2: Int): Boolean {
        return false
    }

    override fun onVideoSizeChanged(p0: IMediaPlayer?, p1: Int, p2: Int, p3: Int, p4: Int) {
    }

    override fun onError(p0: IMediaPlayer?, p1: Int, p2: Int): Boolean {
        return false
    }

    override fun onSeekComplete(p0: IMediaPlayer?) {

    }
}
