package com.ooftf.http.monitor

import android.content.Context
import android.content.Intent
import com.ooftf.http.monitor.ui.SettingActivity

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/6/4
 */
object Monitor {
    var m: MonitorProvider? = null
    fun getSettingIntent(context: Context) = Intent(context, SettingActivity::class.java)
}