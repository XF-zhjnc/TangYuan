package io.github.xf_zhjnc.tangyuantv.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.xf_zhjnc.tangyuantv.bean.LiveBean
import io.github.xf_zhjnc.tangyuantv.http.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LiveDetailViewModel : ViewModel() {

    private val _liveDetailResult = MutableLiveData<LiveBean>()
    val liveDetailResult: LiveData<LiveBean> = _liveDetailResult

    fun requestData(objectId: String, contId: String, dataType: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val query = HashMap<String, String>()
            query["objectContId"] = objectId
            query["contId"] = contId
            query["dataType"] = dataType
            query["stats_menuId"] = "2810" //全部直播id
            query["stats_areaId"] = "2855" //央视频道
            query["stats_areaType"] = "2"
            query["stats_contId"] = objectId
            query["stats_srcContType"] = dataType
            query["stats_srcContId"] = contId
            RetrofitFactory.getApiService().getLiveDetail(query).subscribe {
                _liveDetailResult.postValue(it.liveList[0])
            }
        }
    }
}