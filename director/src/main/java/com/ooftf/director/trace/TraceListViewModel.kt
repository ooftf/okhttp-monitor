package com.ooftf.director.trace

import android.app.Application
import android.content.Intent
import android.os.Build
import com.blankj.utilcode.util.*
import com.ooftf.basic.AppHolder
import com.ooftf.basic.utils.format
import com.ooftf.director.CommonListViewModel
import com.ooftf.director.Item
import com.ooftf.director.info.DebugInfoActivity
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.BasicFileAttributes
import kotlin.io.path.Path

class TraceListViewModel(application: Application) : CommonListViewModel(application) {

    init {
        title.postValue("调试信息")

        items.add(Item("AppVersionName", AppUtils.getAppVersionName()))

        application.getExternalFilesDirs("").filter { it.endsWith(".trace") }.forEach {
            items.add(
                Item(
                    it.name,
                    "${it.totalSpace}        ${TimeUtils.millis2String(it.lastModified())}"
                ) {
                    AppHolder.app.startActivity(
                        Intent(
                            AppHolder.app,
                            DebugInfoActivity::class.java
                        ).apply {
                            putExtra("path", it.absolutePath)
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        })
                })
        }
    }


}