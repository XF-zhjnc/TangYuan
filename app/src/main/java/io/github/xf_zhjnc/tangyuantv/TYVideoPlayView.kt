package io.github.xf_zhjnc.tangyuantv

import android.content.Context
import android.graphics.Color
import android.media.AudioManager
import android.net.Uri
import android.util.AttributeSet
import android.view.Gravity
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.FrameLayout
import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.IjkMediaPlayer


/**
 * NAME: 柚子啊
 * DATE: 2021-01-05
 * DESC: 自定义播放控件
 */
class TYVideoPlayView : FrameLayout {

    private var mContext: Context? = null
    private var mSurfaceView: SurfaceView? = null
    private var mIMediaPlayer: IMediaPlayer? = null
    private var mEnableMediaCodec = false
    private var mListener: TYVideoListener? = null

    /**
     * 视频文件地址
     */
    private var mPath: String? = null

    /**
     * 视频请求header
     */
    private var mHeader: Map<String, String>? = null
    private var mAudioManager: AudioManager? = null
    private var mAudioFocusHelper: AudioFocusHelper? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        mContext = context;
        setBackgroundColor(Color.BLACK)
        createSurfaceView()
        mAudioManager = context.applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        mAudioFocusHelper = AudioFocusHelper()
    }

    /**
     * 创建SurfaceView
     */
    private fun createSurfaceView() {
        mSurfaceView = SurfaceView(mContext)
        mSurfaceView!!.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(surfaceHolder: SurfaceHolder) {

            }

            override fun surfaceChanged(surfaceHolder: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
                mIMediaPlayer?.setDisplay(surfaceHolder)
            }

            override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {

            }
        })

        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER)
        addView(mSurfaceView, 0, layoutParams)
    }

    private fun createPlayer(): IMediaPlayer {
        var ijkMediaPlayer = IjkMediaPlayer()
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "opensles", 1)

        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format", IjkMediaPlayer.SDL_FCC_RV32.toLong())
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 1)
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 0)

        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "http-detect-range-support", 1)

        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 48)
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "min-frames", 100)
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "enable-accurate-seek", 1)

        ijkMediaPlayer.setVolume(1.0f, 1.0f)

        setEnableMediaCodec(ijkMediaPlayer, mEnableMediaCodec)
        return ijkMediaPlayer;
    }

    //设置是否开启硬解码
    private fun setEnableMediaCodec(ijkMediaPlayer: IjkMediaPlayer, isEnable: Boolean) {
        val value = if (isEnable) 1 else 0
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", value.toLong()) //开启硬解码
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", value.toLong())
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", value.toLong())
    }

    fun setEnableMediaCodec(isEnable: Boolean) {
        mEnableMediaCodec = isEnable
    }

    //设置ijkplayer的监听
    private fun setIjkPlayerListener(player: IMediaPlayer) {
        player.setOnPreparedListener(mPreparedListener)
        player.setOnVideoSizeChangedListener(mVideoSizeChangedListener)
    }

    /**
     * 设置自己的player回调
     */
    fun setTYVideoListener(listener: TYVideoListener) {
        mListener = listener
    }

    //设置播放地址
    fun setPath(path: String) {
        mPath = path;
    }

    //    private fun setPath(path: String, header: Map<String?, String?>?) {
    //        mPath = path
    //        mHeader = header
    //    }

    //开始加载视频
    fun load() {
        mIMediaPlayer?.stop()
        mIMediaPlayer?.release()
        mIMediaPlayer = createPlayer()
        setIjkPlayerListener(mIMediaPlayer!!)
        mIMediaPlayer?.setDisplay(mSurfaceView!!.holder)
        mIMediaPlayer?.setDataSource(mContext, Uri.parse(mPath))
        mIMediaPlayer?.prepareAsync()
    }

    fun start() {
        mIMediaPlayer?.start()
        mAudioFocusHelper?.requestFocus()
    }

    fun release() {
        mIMediaPlayer?.reset()
        mIMediaPlayer?.release()
        mIMediaPlayer = null
        mAudioFocusHelper?.abandonFocus()
    }

    fun pause() {
        mIMediaPlayer?.pause()
        mAudioFocusHelper?.abandonFocus()
    }

    fun stop() {
        mIMediaPlayer?.stop()
        mAudioFocusHelper?.abandonFocus()
    }

    fun reset() {
        mIMediaPlayer?.reset()
        mAudioFocusHelper?.abandonFocus()
    }

    fun getDuration(): Long {
        return mIMediaPlayer?.duration ?: 0
    }

    fun getCurrentPosition(): Long {
        return mIMediaPlayer?.currentPosition ?: 0
    }

    fun seekTo(l: Long) {
        mIMediaPlayer?.seekTo(l)
    }

    fun isPlaying(): Boolean {
        return mIMediaPlayer?.isPlaying ?: false
    }


    /**
     * 初始化listener
     */
    private val mPreparedListener = IMediaPlayer.OnPreparedListener { iMediaPlayer ->
        if (mListener != null) {
            mListener!!.onPrepared(iMediaPlayer)
        }
    }

    private val mVideoSizeChangedListener = IMediaPlayer.OnVideoSizeChangedListener { iMediaPlayer, i, i1, i2, i3 ->
        val videoWidth = iMediaPlayer.videoWidth
        val videoHeight = iMediaPlayer.videoHeight
        if (videoWidth != 0 && videoHeight != 0) {
            mSurfaceView?.holder?.setFixedSize(videoWidth, videoHeight)
        }
    }


    /**
     * 音频焦点改变监听
     */
    inner class AudioFocusHelper : AudioManager.OnAudioFocusChangeListener {

        var startRequested = false
        var pausedForLoss = false
        var currentFocus = 0

        override fun onAudioFocusChange(focusChange: Int) {
            if (currentFocus == focusChange) {
                return
            }

            currentFocus = focusChange
            when (focusChange) {
                //获得焦点，暂时获得焦点
                AudioManager.AUDIOFOCUS_GAIN, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT -> {
                    if (startRequested || pausedForLoss) {
                        start()
                        startRequested = false
                        pausedForLoss = false
                    }

                    mIMediaPlayer?.setVolume(1.0f, 1.0f)
                }

                //丢失焦点，暂时丢失焦点
                AudioManager.AUDIOFOCUS_LOSS, AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                    if (isPlaying()) {
                        pausedForLoss = true
                        pause();
                    }
                }

                //需要降低音量
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK                      -> {
                    if (isPlaying()) {
                        mIMediaPlayer?.setVolume(0.1f, 0.1f);
                    }
                }

            }

        }

        fun requestFocus(): Boolean {
            if (currentFocus == AudioManager.AUDIOFOCUS_GAIN) {
                return true
            }
            if (mAudioManager == null) {
                return false
            }
            val status = mAudioManager!!.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
            if (AudioManager.AUDIOFOCUS_REQUEST_GRANTED == status) {
                currentFocus = AudioManager.AUDIOFOCUS_GAIN
                return true
            }
            startRequested = true
            return false
        }

        fun abandonFocus(): Boolean {
            if (mAudioManager == null) {
                return false
            }
            startRequested = false
            val status = mAudioManager!!.abandonAudioFocus(this)
            return AudioManager.AUDIOFOCUS_REQUEST_GRANTED == status
        }
    }
}