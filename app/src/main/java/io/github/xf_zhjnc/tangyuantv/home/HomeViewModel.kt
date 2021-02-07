package io.github.xf_zhjnc.tangyuantv.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.xf_zhjnc.tangyuantv.bean.LiveChannel
import io.github.xf_zhjnc.tangyuantv.http.RetrofitFactory
import kotlin.concurrent.thread

class HomeViewModel : ViewModel() {

    private val _channelListResult = MutableLiveData<List<LiveChannel>>()
    val channelListResult: LiveData<List<LiveChannel>> = _channelListResult

    fun requestList() {
        thread(start = true) {
            println("running from thread(): ${Thread.currentThread()}")

            //RetrofitFactory.getApiService().requestLoading().subscribe()

            RetrofitFactory.getApiService().getAllLive().subscribe {
                _channelListResult.postValue(it.classifyList[0].channelList)
            }
        }

    }

}