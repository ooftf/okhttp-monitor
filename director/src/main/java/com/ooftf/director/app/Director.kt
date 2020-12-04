package com.ooftf.director.app

import android.view.View
import com.didichuxing.doraemonkit.DoraemonKit
import com.lzf.easyfloat.EasyFloat
import com.lzf.easyfloat.permission.PermissionUtils
import com.ooftf.basic.AppHolder
import com.ooftf.director.Item
import com.ooftf.http.monitor.Monitor
import com.ooftf.http.monitor.applyMonitor
import okhttp3.OkHttpClient

object Director {
    var isInit = false
    fun init(doraemonKitProductId: String, debug: Boolean) {
        if (isInit) {
            return
        } else {
            isInit = true
        }
        EasyFloat.init(AppHolder.app, debug)
        Monitor.init()
        DoraemonKit.setAwaysShowMainIcon(false)
        DoraemonKit.install(AppHolder.app, doraemonKitProductId)
        ShowEntranceSwitch.get().takeIf { it }?.takeIf { PermissionUtils.checkPermission(AppHolder.app) }?.let {
            PanelDialog.showFloat(
                    AppHolder.app
            )
        }
    }


    fun setDebugEntranceView(v: View) {
        v.setOnClickListener(object : View.OnClickListener {
            var time = 0L
            var count = 0
            override fun onClick(v: View) {
                val currentTimeMillis = System.currentTimeMillis()
                if (currentTimeMillis - time < 500) {
                    count++
                } else {
                    count = 1

                }
                if (count > 10) {
                    count = 0
                    PanelDialog.openShowFloat(v)
                }
                time = currentTimeMillis
            }

        })
    }

    val customPanelItems by lazy {
        ArrayList<Item>()
    }
    val customDebugItems by lazy {
        ArrayList<Item>()
    }

    /**
     * 添加调试面板
     */
    fun addPanelItem(item: Item) {
        customPanelItems.add(item)
    }

    /**
     * 添加调试界面
     */
    fun addDebugItem(item: Item) {
        customDebugItems.add(item)
    }

    fun applyDirectorInterceptor(build: OkHttpClient.Builder): OkHttpClient.Builder {
        return build.applyDirectorInterceptor()
    }
}

fun OkHttpClient.Builder.applyDirectorInterceptor(): OkHttpClient.Builder {
    return applyMonitor()
}