package io.github.xf_zhjnc.tangyuantv.bean

data class LiveClassify(
    val moduleId: Int,
    val moduleName: String,
    val requestURL: String,
    val channelList: List<LiveChannel>
)
