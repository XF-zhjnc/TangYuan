package io.github.xf_zhjnc.tangyuantv.http

import android.util.Log
import io.github.xf_zhjnc.tangyuantv.BuildConfig
import kotlin.collections.HashMap

/**
 * NAME: vSimpleton
 * DATE: 2021-02-03
 * DESC: 构造请求参数
 */

class ApiHelper {

    companion object {
        fun getPostParams(param: HashMap<String, Any>): HashMap<String, Any> {
            val paramMap = HashMap<String, Any>()
            if (BuildConfig.DEBUG) {
                Log.i("params", """requestParam:$paramMap""".trimIndent())
            }
            paramMap["xxx"] = 123
            paramMap.putAll(param)
            return paramMap
        }

    }

}