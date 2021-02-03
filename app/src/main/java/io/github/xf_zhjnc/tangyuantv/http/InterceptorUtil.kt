package io.github.xf_zhjnc.tangyuantv.http

import android.util.Log
import io.github.xf_zhjnc.tangyuantv.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

/**
 * NAME: vSimpleton
 * DATE: 2021-02-03
 * DESC: HTTP拦截器
 */

class InterceptorUtil {

    companion object {
        private var LOG_MAX_LENGTH = 2000

        //日志拦截器
        fun LogInterceptor(): HttpLoggingInterceptor? {
            return HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    val strLength = message.length
                    var start = 0
                    var end: Int = InterceptorUtil.LOG_MAX_LENGTH
                    for (i in 0..99) {
                        //剩下的文本还是大于规定长度则继续重复截取并输出
                        if (strLength > end) {
                            if (BuildConfig.DEBUG) {
                                Log.i(
                                    "requestUrl",
                                    "log: " + message.substring(start, end)
                                )
                            }
                            start = end
                            end += InterceptorUtil.LOG_MAX_LENGTH
                        } else {
                            if (BuildConfig.DEBUG) {
                                Log.i(
                                    "requestUrl",
                                    "log: " + message.substring(start, strLength)
                                )
                            }
                            break
                        }
                    }
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY) //设置打印数据的级别
        }
    }

}