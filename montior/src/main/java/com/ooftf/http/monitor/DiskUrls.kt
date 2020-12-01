package com.ooftf.http.monitor

import com.google.gson.Gson
import com.ooftf.basic.engine.serializable.SerializableObject
import java.util.concurrent.CopyOnWriteArrayList

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/6/10
 */
object DiskUrls : SerializableObject<CopyOnWriteArrayList<String>>() {
    override fun getDefaultValue(): CopyOnWriteArrayList<String> {
        return CopyOnWriteArrayList()
    }
}