package io.github.xf_zhjnc.tangyuantv.http.vo

import io.github.xf_zhjnc.tangyuantv.bean.LiveBean

data class LiveChannelDetailResult(
    val dayVoList: List<DayVo>,
    val liveList: List<LiveBean>
)