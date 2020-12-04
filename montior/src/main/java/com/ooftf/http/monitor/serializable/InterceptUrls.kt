package com.ooftf.http.monitor.serializable

import com.ooftf.basic.engine.serializable.SerializableObject
import java.util.concurrent.CopyOnWriteArraySet

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/12/4
 */
object InterceptUrls : SerializableObject<CopyOnWriteArraySet<String>>() {
    override fun getDefaultValue(): CopyOnWriteArraySet<String> {
        return CopyOnWriteArraySet()
    }
}