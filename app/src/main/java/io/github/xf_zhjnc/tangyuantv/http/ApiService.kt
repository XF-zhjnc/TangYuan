package io.github.xf_zhjnc.tangyuantv.http

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

//@JvmSuppressWildcards 用来注解类和方法，使得被标记元素的泛型参数不会被编译成通配符?
@JvmSuppressWildcards
interface ApiService {

    @FormUrlEncoded
    @POST("/api/{api_version}/login")
    fun login(@Path("api_version") apiVersion: String,
              @Field("username") username: String,
              @Field("password") password: String): Call<ResponseBody>

}