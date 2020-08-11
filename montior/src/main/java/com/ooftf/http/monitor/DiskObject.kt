package com.ooftf.http.monitor

import android.content.Context
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.CopyOnWriteArrayList

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/6/10
 */
class DiskObject<T : Any> {
    val sp = Monitor.monitorProvider?.getApplication()
        ?.getSharedPreferences("DiskObject" + javaClass.simpleName, Context.MODE_PRIVATE)
    val type = getParamType()
    fun put(item: T) {
        sp?.edit()?.putString(javaClass.simpleName, Monitor.monitorProvider?.object2Json(item))
            ?.apply()
    }

    fun get(): T? {
        if (type == null) return null
        val s = sp?.getString(javaClass.simpleName, null) ?: return null
        return Monitor.monitorProvider?.parseObject<T>(s, type)
    }

    fun getParamType(): Type? {
        val superClass = javaClass.genericSuperclass
        if (superClass !is ParameterizedType) {
            return null
        }
        if (superClass.actualTypeArguments.isEmpty()) {
            return null
        }
        return superClass.actualTypeArguments[0]
    }
}