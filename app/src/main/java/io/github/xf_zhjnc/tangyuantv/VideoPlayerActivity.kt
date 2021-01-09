package io.github.xf_zhjnc.tangyuantv

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.AudioManager.STREAM_MUSIC
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.github.xf_zhjnc.tangyuantv.databinding.ActivityVideoPlayerBinding
import io.github.xf_zhjnc.tangyuantv.event.TYGestureListener
import io.github.xf_zhjnc.tangyuantv.utils.BrightnessHelper
import tv.danmaku.ijk.media.player.IMediaPlayer
import java.util.*


/**
 * NAME: 柚子啊
 * DATE: 2021-01-05
 * DESC: 视频播放页
 */
class VideoPlayerActivity : AppCompatActivity(), TYVideoListener, View.OnClickListener,
                            TYGestureListener {

    private var videoPath: String? = null
    private lateinit var mBinding: ActivityVideoPlayerBinding
    private var currentTime: Long = System.currentTimeMillis()
    private var menuVisible: Boolean = true
    private lateinit var timer: Timer

    //调节音量相关
    private lateinit var mAudioManager: AudioManager
    private var maxVolume = 0
    private var oldVolume = 0

    //调节亮度相关
    private lateinit var mBrightnessHelper: BrightnessHelper
    private var brightness = 1f
    private lateinit var mLayoutParams: WindowManager.LayoutParams

    companion object {
        const val VIDEO_PATH = "video_path"

        fun start(context: Context, url: String) {
            val intent = Intent(context, VideoPlayerActivity::class.java)
            intent.putExtra(VIDEO_PATH, url)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initView()
        initVideoPlayer()
        initListener()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_IMMERSIVE or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    private fun initView() {
        mBinding.playerController.ivPlayOrPause.setImageResource(R.drawable.ic_player_pause)
        timer = Timer()

        mAudioManager = getSystemService(Service.AUDIO_SERVICE) as AudioManager
        maxVolume = mAudioManager.getStreamMaxVolume(STREAM_MUSIC)

        mBrightnessHelper = BrightnessHelper(this)
        //设置当前APP亮度的方法配置
        mLayoutParams = window.attributes
        brightness = mLayoutParams.screenBrightness
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
        mBinding.clBaseLayout.setTYGestureListener(this)
    }

    override fun onStart() {
        super.onStart()
        showTopView()
    }

    override fun onResume() {
        super.onResume()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onPause() {
        super.onPause()
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private fun showTopView() {
        menuVisible = true
        mBinding.clTopViewLayout.visibility = View.VISIBLE
        currentTime = System.currentTimeMillis()

        val timerTask = object : TimerTask() {
            override fun run() {
                runOnUiThread {
//                        val animation: Animation = AnimationUtils.loadAnimation(applicationContext,
//                                                                                R.anim.anim_move_top)
//                        mBinding.clTopViewLayout.startAnimation(animation)
                    mBinding.clTopViewLayout.visibility = View.GONE
                }
                menuVisible = false
            }
        }
        timer.schedule(timerTask, 2000)
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
        timer.cancel()
    }

    /**
     * 手势调节亮度
     */
    override fun onBrightnessGesture(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float,
                                     distanceY: Float) {

        e1 ?: return
        e2 ?: return

        var newBrightness: Float = (e1.y - e2.y) / mBinding.clBaseLayout.height
        newBrightness += brightness
        if (newBrightness < 0f) {
            newBrightness = 0f
        } else if (newBrightness > 1f) {
            newBrightness = 1f
        }

        mLayoutParams.screenBrightness = newBrightness
        window.attributes = mLayoutParams
        mBinding.gestureLayout.setProgress((newBrightness * 100).toInt())
        mBinding.gestureLayout.setImageResource(R.drawable.ic_brightness)
        mBinding.gestureLayout.show()

    }

    /**
     * 直接设置系统亮度的方法
     */
    private fun setBrightness(brightness: Int) {
        //要是有自动调节亮度，把它关掉
        mBrightnessHelper.offAutoBrightness()
        val oldBrightness = mBrightnessHelper.getBrightness()
        val newBrightness = oldBrightness + brightness
        //设置亮度
        mBrightnessHelper.setSystemBrightness(newBrightness)
        //设置显示
        mBinding.gestureLayout.setProgress((java.lang.Float.valueOf(
                newBrightness.toFloat()) / mBrightnessHelper.getMaxBrightness() * 100).toInt())
        mBinding.gestureLayout.setImageResource(R.drawable.ic_brightness)
        mBinding.gestureLayout.show()
    }

    /**
     * 手势调节音量
     */
    override fun onVolumeGesture(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float,
                                 distanceY: Float) {

        e1 ?: return
        e2 ?: return

        val value = mBinding.clBaseLayout.height / maxVolume
        val newVolume = ((e1.y - e2.y) / value + oldVolume).toInt()
        mAudioManager.setStreamVolume(STREAM_MUSIC, newVolume, AudioManager.FLAG_PLAY_SOUND)

        val volumeProgress = (newVolume / maxVolume.toFloat() * 100).toInt()

        when {
            newVolume > oldVolume          -> {
                mBinding.gestureLayout.setImageResource(R.drawable.ic_volume_add)
            }
            newVolume in 1 .. oldVolume -> {
                mBinding.gestureLayout.setImageResource(R.drawable.ic_volume_reduce)
            }
            else                           -> {
                mBinding.gestureLayout.setImageResource(R.drawable.ic_volume_mute)
            }
        }

        mBinding.gestureLayout.setProgress(volumeProgress)
        mBinding.gestureLayout.show()

    }

    override fun onSingleTapGesture(e: MotionEvent?) {

    }

    override fun onDoubleTapGesture(e: MotionEvent?) {

    }

    override fun onDown(e: MotionEvent?) {
        showTopView()

        oldVolume = mAudioManager.getStreamVolume(STREAM_MUSIC)
        brightness = mLayoutParams.screenBrightness
        if (brightness == -1f) {
            //一开始是默认亮度的时候，获取系统亮度，计算比例值
            brightness = mBrightnessHelper.getBrightness() / 255f
        }
    }
}
