package com.ooftf.http.monitor.serializable

import com.ooftf.basic.engine.serializable.SerializableBoolean

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/12/3
 */
object ReviseSwitch : SerializableBoolean() {
    override fun getKey(): String {
        return javaClass.name
    }
}