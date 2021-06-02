package com.ooftf.director.trace

import android.app.Application
import android.content.Intent
import com.blankj.utilcode.util.TimeUtils
import com.ooftf.basic.AppHolder
import com.ooftf.basic.utils.format
import com.ooftf.director.CommonListViewModel
import com.ooftf.director.Item

class TraceListViewModel(application: Application) : CommonListViewModel(application) {

    init {
        title.postValue("调试信息")
        application.getExternalFilesDir("").listFiles()?.filter { it.name.endsWith(".trace") }?.forEach {
            items.add(
                Item(
                    it.name,
                    "${(it.length().toFloat()/1024/1024).format("0.00")}MB        ${TimeUtils.millis2String(it.lastModified())}"
                ) {
                    AppHolder.app.startActivity(
                        Intent(
                            AppHolder.app,
                            TraceDetailActivity::class.java
                        ).apply {
                            putExtra("path", it.absolutePath)
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        })
                })
        }
    }


}