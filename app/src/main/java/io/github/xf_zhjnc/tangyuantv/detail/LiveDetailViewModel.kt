package io.github.xf_zhjnc.tangyuantv.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.xf_zhjnc.tangyuantv.http.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LiveDetailViewModel : ViewModel() {

    fun requestData() {
        viewModelScope.launch(Dispatchers.IO) {
            val query = HashMap<String, String>()
            query["objectContId"] = "1803596"
            RetrofitFactory.getApiService().getLiveDetail(query).subscribe()
        }
    }
}