package com.ooftf.director.app

import android.view.View
import com.ooftf.director.Item
import okhttp3.OkHttpClient

object Director {
    fun init(doraemonKitProductId: String, debug: Boolean) {

    }

    fun setDebugEntranceView(v: View) {

    }


    /**
     * 添加调试面板
     */
    fun addPanelItem(item: Item) {

    }

    /**
     * 添加调试界面
     */
    fun addDebugItem(item: Item) {

    }

    fun applyDirectorInterceptor(build: OkHttpClient.Builder): OkHttpClient.Builder {
        return build
    }
}

fun OkHttpClient.Builder.applyDirectorInterceptor(): OkHttpClient.Builder {
    return applyMonitor()
}

private fun OkHttpClient.Builder.applyMonitor(): OkHttpClient.Builder {
    return this
}