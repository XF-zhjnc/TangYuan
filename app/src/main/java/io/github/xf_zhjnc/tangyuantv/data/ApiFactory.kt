package io.github.xf_zhjnc.tangyuantv.data

import android.util.Log
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder
import java.util.concurrent.TimeUnit

object ApiFactory {

    private val mLoggingInterceptor: Interceptor by lazy { LoggingInterceptor() }

    private val mClient: OkHttpClient by lazy { newClient() }

    fun <T> create(baseUrl: String, clazz: Class<T>): T = Retrofit.Builder().baseUrl(baseUrl).client(mClient)
            .addConverterFactory(GsonConverterFactory.create())
            //.addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build().create(clazz)

    private fun newClient(): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(30, TimeUnit.SECONDS)
        readTimeout(10, TimeUnit.SECONDS)
        writeTimeout(10, TimeUnit.SECONDS)
        addInterceptor(mLoggingInterceptor)
    }.build()

    private class LoggingInterceptor: Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder = StringBuilder()
            val startTime = System.nanoTime()
            val response: Response = with(chain.request()) {
                builder.append(method + "\n")
                builder.append("Sending request\n$url")
                if (method == "POST") {
                    builder.append("?")
                    when (val body = body) {
                        is FormBody -> {
                            for (j in 0 until body.size) {
                                builder.append(body.name(j) + "=" + body.value(j))
                                if (j != body.size - 1) {
                                    builder.append("&")
                                }
                            }
                        }
                    }
                }
                builder.append("\n").append(headers)
                chain.proceed(this)
            }
            builder.append("Received response in " + (System.nanoTime() - startTime) / 1e6 + "ms\n")
            builder.append("code " + response.code + "\n")
            Log.v("ApiFactory", builder.toString())
            return response
        }
    }
}