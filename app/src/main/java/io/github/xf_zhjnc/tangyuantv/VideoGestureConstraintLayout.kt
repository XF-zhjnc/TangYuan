package io.github.xf_zhjnc.tangyuantv

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.annotation.IntDef
import androidx.constraintlayout.widget.ConstraintLayout
import io.github.xf_zhjnc.tangyuantv.event.TYGestureListener
import io.github.xf_zhjnc.tangyuantv.utils.ScreenUtils
import kotlin.math.abs


/**
 * NAME: 柚子啊
 * DATE: 2021-01-08
 * DESC: 带有手势控制的ConstraintLayout
 */
class VideoGestureConstraintLayout : ConstraintLayout {

    companion object {
        const val NONE = 0
        const val VOLUME = 1
        const val BRIGHTNESS = 2
    }

    @ScrollMode
    private var mScrollMode = NONE

    private lateinit var mOnGestureListener: VideoPlayerOnGestureListener
    private lateinit var mGestureDetector: GestureDetector
    private lateinit var mTYGestureListener: TYGestureListener
    private val offsetX = 1

    @IntDef(NONE, VOLUME, BRIGHTNESS)
    @Retention(AnnotationRetention.SOURCE)
    private annotation class ScrollMode {

    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs,
                                                                                   defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        mOnGestureListener = VideoPlayerOnGestureListener()
        mGestureDetector = GestureDetector(context, mOnGestureListener)
        mGestureDetector.setIsLongpressEnabled(false) //取消长按功能，否则会影响滑动判断

        setOnTouchListener { _, event -> mGestureDetector.onTouchEvent(event) }

    }


    inner class VideoPlayerOnGestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent?): Boolean {
            //每次按下都重置为NONE
            mScrollMode = NONE
            mTYGestureListener.onDown(e)
            return true
        }

        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float,
                              distanceY: Float): Boolean {
            Log.d("youzi", "onScroll: e1:" + e1.x + "," + e1.y)
            Log.d("youzi", "onScroll: e2:" + e2.x + "," + e2.y)
            Log.d("youzi", "onScroll: X:$distanceX  Y:$distanceY")
            when (mScrollMode) {
                NONE       -> {
                    Log.d("youzi", "NONE: ")
                    if (abs(distanceX) - abs(distanceY) > offsetX) {

                    } else {
                        mScrollMode = if (e1.x < ScreenUtils.getScreenWidth() / 2) {
                            BRIGHTNESS
                        } else {
                            VOLUME
                        }
                    }
                }

                VOLUME     -> {
                    Log.d("youzi", "VOLUME: ")
                    mTYGestureListener.onVolumeGesture(e1, e2, distanceX, distanceY)
                }

                BRIGHTNESS -> {
                    Log.d("youzi", "BRIGHTNESS: ")
                    mTYGestureListener.onBrightnessGesture(e1, e2, distanceX, distanceY);
                }

            }
            return true
        }

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            Log.d("youzi", "onDoubleTap: ");
            mTYGestureListener.onDoubleTapGesture(e)
            return super.onDoubleTap(e)
        }

        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            Log.d("youzi", "onSingleTapConfirmed: ");
            mTYGestureListener.onSingleTapGesture(e)
            return super.onSingleTapConfirmed(e)
        }

    }

    fun setTYGestureListener(tyGestureListener: TYGestureListener) {
        mTYGestureListener = tyGestureListener
    }

}