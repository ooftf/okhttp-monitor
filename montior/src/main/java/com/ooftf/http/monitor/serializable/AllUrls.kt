package com.ooftf.http.monitor.serializable

import com.ooftf.basic.engine.serializable.SerializableObject
import java.util.concurrent.CopyOnWriteArrayList

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/6/10
 */
object AllUrls : SerializableObject<CopyOnWriteArrayList<String>>() {
    override fun getDefaultValue(): CopyOnWriteArrayList<String> {
        return CopyOnWriteArrayList<String>()
    }
}