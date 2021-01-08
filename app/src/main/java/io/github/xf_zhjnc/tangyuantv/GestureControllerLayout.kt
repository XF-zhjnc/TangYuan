package io.github.xf_zhjnc.tangyuantv

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import io.github.xf_zhjnc.tangyuantv.databinding.ViewGestureVolumeBrightnessBinding


/**
 * NAME: 柚子啊
 * DATE: 2021-01-08
 * DESC: 调整亮度/音量框
 */
class GestureControllerLayout : ConstraintLayout {

    private lateinit var mBinding: ViewGestureVolumeBrightnessBinding
    private var mHideRunnable: HideRunnable? = null
    private var duration = 1000

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs,
                                                                                   defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        mBinding = ViewGestureVolumeBrightnessBinding.inflate(LayoutInflater.from(context), null,
                                                              false)
        addView(mBinding.root)
        mHideRunnable = HideRunnable()
        visibility = View.GONE
    }

    fun show() {
        visibility = View.VISIBLE
        removeCallbacks(mHideRunnable)
        postDelayed(mHideRunnable, duration.toLong())
    }

    /**
     * 设置进度
     */
    fun setProgress(progress: Int) {
        mBinding.pb.progress = progress
    }

    /**
     * 设置持续时间
     */
    fun setDuration(duration: Int) {
        this.duration = duration
    }

    /**
     * 设置显示图片
     */
    fun setImageResource(resource: Int) {
        mBinding.ivImg.setImageResource(resource)
    }

    /**
     * 当调整过亮度或音量后延时隐藏view
     */
    inner class HideRunnable : Runnable {
        override fun run() {
            this@GestureControllerLayout.visibility = View.GONE
        }
    }
}