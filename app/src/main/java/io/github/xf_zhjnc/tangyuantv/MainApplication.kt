package io.github.xf_zhjnc.tangyuantv

import android.app.Application
import android.content.Context

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        private lateinit var context: Context

        fun getContext(): Context {
            return context
        }
    }

}