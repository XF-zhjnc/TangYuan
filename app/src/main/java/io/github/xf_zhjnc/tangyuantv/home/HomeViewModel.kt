package io.github.xf_zhjnc.tangyuantv.home

import androidx.lifecycle.ViewModel
import io.github.xf_zhjnc.tangyuantv.http.RetrofitFactory
import kotlin.concurrent.thread

class HomeViewModel : ViewModel() {

    fun requestList() {
        thread(start = true) {
            println("running from thread(): ${Thread.currentThread()}")

            RetrofitFactory.getApiService().requestLoading().subscribe()

        }

    }

}