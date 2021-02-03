package io.github.xf_zhjnc.tangyuantv.http

import android.util.Log
import io.github.xf_zhjnc.tangyuantv.BuildConfig
import io.github.xf_zhjnc.tangyuantv.utils.DESUtil
import io.github.xf_zhjnc.tangyuantv.utils.Md5Utils
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
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

        fun HeaderInterceptor(): Interceptor {
            return Interceptor {
                val timestamp = System.currentTimeMillis().toString()
                val commonParams = ApiHelper.COMMON_PARAMS.format(timestamp)
                val filter = Md5Utils.getHmacMd5Str("72116AcB!94C4*4F89#k76BdB", commonParams)
                val afas = DESUtil.encryptMode(commonParams)?:""

                val request: Request = it.request().newBuilder()
                    .addHeader("Connection", "Keep-Alive")
                    .addHeader("Accept-Encoding", "gzip")
                    .addHeader("Host", "m.cctv4g.com")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Timestamp", timestamp)
                    .addHeader("User-Agent", "yichengtianxia")
                    .addHeader("Commmonparams", commonParams)
                    .addHeader("Filter", filter)
                    .addHeader("Afas", afas)
                    .build()
                return@Interceptor it.proceed(request)
            }
        }

        fun CommonQueryInterceptor(): Interceptor {
            return Interceptor {

                val authorizedUrlBuilder: HttpUrl.Builder = it.request().url
                    .newBuilder()
                    .scheme(it.request().url.scheme)
                    .host(it.request().url.host)
                    .addQueryParameter("wdChannelName", "qq")
                    .addQueryParameter("wdVersionName", "3.5.2")
                    .addQueryParameter("wdClientType", "1")
                    .addQueryParameter("wdAppId", "3")
                    .addQueryParameter("wdNetType", "WIFI")
                    .addQueryParameter("uuid", ApiHelper.uuid)
                    .addQueryParameter("channel", "cctv")

                val request: Request = it.request().newBuilder()
                    .method(it.request().method, it.request().body)
                    .url(authorizedUrlBuilder.build())
                    .build()
                return@Interceptor it.proceed(request)
            }
        }
    }

}