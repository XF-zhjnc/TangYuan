package io.github.xf_zhjnc.tangyuantv.http.vo

import io.github.xf_zhjnc.tangyuantv.bean.LiveClassify

data class AllLiveResult(
    val resultCode: String,
    val classifyList: List<LiveClassify>
)