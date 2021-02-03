package io.github.xf_zhjnc.tangyuantv.http

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

//@JvmSuppressWildcards 用来注解类和方法，使得被标记元素的泛型参数不会被编译成通配符?
@JvmSuppressWildcards
interface ApiService {

    @POST("cntv/resource/cltv2/loading.jsp")
    fun requestLoading(): Observable<ResponseBody>
}