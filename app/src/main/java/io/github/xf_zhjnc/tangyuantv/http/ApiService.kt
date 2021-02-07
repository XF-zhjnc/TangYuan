package io.github.xf_zhjnc.tangyuantv.http

import io.github.xf_zhjnc.tangyuantv.http.vo.AllLiveResult
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

//@JvmSuppressWildcards 用来注解类和方法，使得被标记元素的泛型参数不会被编译成通配符?
@JvmSuppressWildcards
interface ApiService {

    @POST("cntv/resource/cltv2/loading.jsp")
    fun requestLoading(): Observable<ResponseBody>

    @POST("cntv/resource/cltv2/allLive.jsp?nodeId=1438")
    fun getAllLive(): Observable<AllLiveResult>

    @POST("cntv/resource/cltv2/liveDetailPage.jsp")
    fun getLiveDetail(@QueryMap query: Map<String, String>): Observable<ResponseBody>

    @POST("cntv/clt/programAuthAndGetPlayUrl.msp")
    fun getPlayUrl() : Observable<ResponseBody>
}