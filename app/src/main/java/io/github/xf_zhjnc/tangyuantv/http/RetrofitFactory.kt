package io.github.xf_zhjnc.tangyuantv.http

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * NAME: vSimpleton
 * DATE: 2021-02-03
 * DESC: Retrofit网络请求工具类
 */

object RetrofitFactory {

    init {
        initRetrofit()
    }

    private lateinit var mApiService: ApiService

    private fun initRetrofit() {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder().connectTimeout(
                20 * 1000.toLong(), TimeUnit.MILLISECONDS
            ) //20秒链接超时
            .writeTimeout(20 * 1000.toLong(), TimeUnit.MILLISECONDS) //写入超时20秒
            .addInterceptor(InterceptorUtil.LogInterceptor()!!) //添加日志拦截器
            .readTimeout(20 * 1000.toLong(), TimeUnit.MILLISECONDS)


        val mRetrofit: Retrofit = Retrofit.Builder().baseUrl(ApiConstants.BASE_URL).addConverterFactory(GsonConverterFactory.create()) //添加Gson转换器
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //添加RxJava转换器
            .client(builder.build()).build()

        mApiService = mRetrofit.create(ApiService::class.java)
    }

    fun getApiService(): ApiService {
        return mApiService
    }

}