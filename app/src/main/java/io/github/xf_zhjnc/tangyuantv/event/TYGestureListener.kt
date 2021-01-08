package io.github.xf_zhjnc.tangyuantv.event

import android.view.MotionEvent




/**
 * NAME: 柚子啊
 * DATE: 2021-01-08
 * DESC: 视频播放时的相关手势控制
 */
interface TYGestureListener {

    //亮度手势，手指在Layout左半部上下滑动时候调用
    fun onBrightnessGesture(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float)

    //音量手势，手指在Layout右半部上下滑动时候调用
    fun onVolumeGesture(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float)

    //单击手势，确认是单击的时候调用
    fun onSingleTapGesture(e: MotionEvent?)

    //双击手势，确认是双击的时候调用
    fun onDoubleTapGesture(e: MotionEvent?)

    //按下手势，第一根手指按下时候调用
    fun onDown(e: MotionEvent?)

}