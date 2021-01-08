package io.github.xf_zhjnc.tangyuantv.utils

import android.app.Activity
import android.content.Context
import android.provider.Settings
import android.view.Window
import android.view.WindowManager


/**
 * NAME: 柚子啊
 * DATE: 2021-01-08
 * DESC: 调节亮度--系统orAPP
 */
class BrightnessHelper(context: Context) {

    private var resolver = context.contentResolver
    private val maxBrightness = 255

    /**
     * 调整亮度范围--屏幕最大亮度为255，最小为0
     */
    private fun adjustBrightnessNumber(brightness: Int): Int {
        var mBrightness = brightness
        if (brightness < 0) {
            mBrightness = 0
        } else if (brightness > 255) {
            mBrightness = 255
        }
        return mBrightness
    }

    /**
     * 关闭自动调节亮度
     */
    fun offAutoBrightness() {
        try {
            if (Settings.System.getInt(resolver,
                                       Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
            ) {
                Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS_MODE,
                                       Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
            }
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
        }
    }

    /**
     * 获取系统亮度
     */
    fun getBrightness(): Int {
        return Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS, 255)
    }

    /**
     * 设置系统亮度，如果有设置了自动调节，请先调用offAutoBrightness()方法关闭自动调节，否则会设置失败
     */
    fun setSystemBrightness(newBrightness: Int) {
        Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS,
                               adjustBrightnessNumber(newBrightness))
    }

    fun getMaxBrightness(): Int {
        return maxBrightness
    }

    /**
     * 设置当前APP的亮度
     */
    fun setAppBrightness(brightnessPercent: Float, activity: Activity) {
        val window: Window = activity.window
        val layoutParams: WindowManager.LayoutParams = window.attributes
        layoutParams.screenBrightness = brightnessPercent
        window.attributes = layoutParams
    }
}