package io.github.xf_zhjnc.tangyuantv.utils

import android.content.Context
import android.graphics.Point
import android.view.WindowManager
import io.github.xf_zhjnc.tangyuantv.MainApplication

class ScreenUtils {
    companion object {
        fun dp2px(dpValue: Float): Int {
            val scale: Float = MainApplication.getContext().resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        fun getScreenWidth(): Int {
            val wm = MainApplication.getContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val point = Point()
            wm.defaultDisplay.getRealSize(point)
            return point.x
        }

        fun getScreenHeight(): Int {
            val wm = MainApplication.getContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val point = Point()
            wm.defaultDisplay.getRealSize(point)
            return point.y
        }
    }
}