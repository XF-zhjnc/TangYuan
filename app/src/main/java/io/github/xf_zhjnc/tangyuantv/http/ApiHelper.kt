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

        private const val marketId = "qq"
        private const val appVersion = "3.5.2"
        private const val deviceType = "ONEPLUS A3010"
        private const val clientId = "AhpJR0VF6ijfNWR437XZTGZ-UX8pjmKpZ4-ifyyR_YW4"
        private const val osVersion = 9
        private const val allNetworkType = "WIFI"
        const val uuid = "3792d395-0605-33c8-a6a0-f68af95d751d"
        private const val channel = "cctv"

        //拼接在url后
        private const val PARAMS =
            "?wdChannelName=$marketId&wdVersionName=$appVersion&wdClientType=1&wdAppId=3&wdNetType=$allNetworkType&uuid=$uuid&channel=$channel"

        //用于header拦截
        const val COMMON_PARAMS = "wdVersionName=$appVersion&wdClientType=1&timestamp=%s&uuid=$uuid"

        private const val OTHER_PARAMS =
            "&wdChannelName=$marketId&wdVersionName=$appVersion&wdClientType=1&wdAppId=3&wdNetType=$allNetworkType&uuid=$uuid&channel=$channel"

        fun getPostParams(param: HashMap<String, Any>): HashMap<String, Any> {
            val paramMap = HashMap<String, Any>()
            if (BuildConfig.DEBUG) {
                Log.i("params", """requestParam:$paramMap""".trimIndent())
            }

            paramMap["wdChannelName"] = marketId
            paramMap["wdVersionName"] = appVersion
            paramMap["deviceType"] = deviceType
            paramMap["clientId"] = clientId
            paramMap["osVersion"] = osVersion
            paramMap["wdClientType"] = 1
            paramMap["wdAppId"] = 3
            paramMap["wdNetType"] = allNetworkType
            paramMap["uuid"] = uuid
            paramMap["channel"] = channel

            paramMap.putAll(param)
            return paramMap
        }

    }

}