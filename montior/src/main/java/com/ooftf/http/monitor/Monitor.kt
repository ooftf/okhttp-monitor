package com.ooftf.http.monitor

import android.content.Context
import android.content.Intent
import com.ooftf.http.monitor.ui.SettingActivity
import com.readystatesoftware.chuck.Chuck

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/6/4
 */
object Monitor {
    internal var monitorProvider: MonitorProvider? = null
    fun getSettingIntent(context: Context) = Intent(context, SettingActivity::class.java)
    fun getLogViewIntent(context: Context) = Chuck.getLaunchIntent(context)
    fun init(provider: MonitorProvider) {
        monitorProvider = provider
    }


}