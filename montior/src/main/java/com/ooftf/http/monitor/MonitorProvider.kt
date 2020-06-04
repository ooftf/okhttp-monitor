package com.ooftf.http.monitor

import android.app.Activity
import android.app.Application

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/6/4
 */
interface MonitorProvider {
    fun getTopActivity(): Activity?
    fun getApplication(): Application
    fun isInterceptLog(): Boolean
}