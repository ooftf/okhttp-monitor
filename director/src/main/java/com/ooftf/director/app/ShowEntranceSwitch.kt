package com.ooftf.director.app
import com.ooftf.basic.engine.serializable.SerializableBoolean
import com.ooftf.director.app.DebugConst.KV_KEV_SHOW_ENTRANCE

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/7/7
 */
object ShowEntranceSwitch : SerializableBoolean() {

    override fun getKey(): String {
        return KV_KEV_SHOW_ENTRANCE
    }
}