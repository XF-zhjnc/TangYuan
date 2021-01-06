package io.github.xf_zhjnc.tangyuantv

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.github.xf_zhjnc.tangyuantv.databinding.ActivityVideoPlayerBinding
import tv.danmaku.ijk.media.player.IMediaPlayer
import java.util.*


/**
 * NAME: 柚子啊
 * DATE: 2021-01-05
 * DESC: 视频播放页
 */
class VideoPlayerActivity : AppCompatActivity(), TYVideoListener, View.OnClickListener {

    private var videoPath: String? = null
    private lateinit var mBinding: ActivityVideoPlayerBinding
    private var currentTime: Long = System.currentTimeMillis()
    private var menuVisible: Boolean = true
    private var timer: Timer? = null

    companion object {
        const val VIDEO_PATH = "video_path"

        fun start(context: Context, url: String) {
            val intent = Intent(context, VideoPlayerActivity::class.java)
            intent.putExtra(VIDEO_PATH, url)
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

        initView()
        initVideoPlayer()
        initListener()
    }

    private fun initView() {
        mBinding.playerController.ivPlayOrPause.setImageResource(R.drawable.ic_player_pause)

        timer = Timer()
        val timerTask = object : TimerTask() {
            override fun run() {
                val t = System.currentTimeMillis()
                if (t - currentTime > 2000 && menuVisible) {
                    currentTime = t
                    runOnUiThread {
                        Toast.makeText(this@VideoPlayerActivity, "隐藏头部", Toast.LENGTH_SHORT).show()
//                        val animation: Animation = AnimationUtils.loadAnimation(applicationContext,
//                                                                                R.anim.anim_move_top)
//                        mBinding.clTopViewLayout.startAnimation(animation)
                        mBinding.clTopViewLayout.visibility = View.GONE
                    }
                    menuVisible = false
                }
            }
        }

        timer!!.schedule(timerTask, 500)

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!menuVisible) {
                    Toast.makeText(this@VideoPlayerActivity, "显示头部", Toast.LENGTH_SHORT).show()
                    menuVisible = true
                    mBinding.clTopViewLayout.visibility = View.VISIBLE
                    currentTime = System.currentTimeMillis()
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun initVideoPlayer() {
        videoPath = intent.getStringExtra(VIDEO_PATH)
        mBinding.videoPlayView.setTYVideoListener(this)
        videoPath?.let { mBinding.videoPlayView.setPath(it) }
        mBinding.videoPlayView.load()
    }

    private fun initListener() {
        mBinding.playerController.ivPlayOrPause.setOnClickListener(this)
        mBinding.ivBack.setOnClickListener(this)
        mBinding.ivTVChannel.setOnClickListener(this)
    }

    override fun onBufferingUpdate(p0: IMediaPlayer?, p1: Int) {

    }

    override fun onCompletion(p0: IMediaPlayer?) {

    }

    override fun onPrepared(p0: IMediaPlayer?) {
        mBinding.videoPlayView.start()
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

    override fun onClick(view: View?) {
        when (view) {
            mBinding.playerController.ivPlayOrPause -> {
                if (mBinding.videoPlayView.isPlaying()) {
                    mBinding.videoPlayView.pause()
                    mBinding.playerController.ivPlayOrPause.setImageResource(
                            R.drawable.ic_player_play)
                } else {
                    mBinding.videoPlayView.start()
                    mBinding.playerController.ivPlayOrPause.setImageResource(
                            R.drawable.ic_player_pause)
                }
            }

            mBinding.ivBack                         -> finish()

            mBinding.ivTVChannel                    -> Toast
                .makeText(this, "选择频道列表", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}
