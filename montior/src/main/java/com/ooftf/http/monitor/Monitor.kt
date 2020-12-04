package com.ooftf.http.monitor

import android.content.Context
import android.content.Intent
import androidx.appcompat.widget.SwitchCompat
import com.ooftf.basic.AppHolder
import com.ooftf.basic.armor.ActivityLifecycleCallbacksArmor
import com.ooftf.http.monitor.interceptor.ChuckInterceptorPro
import com.ooftf.http.monitor.interceptor.ReviseInterceptor
import com.ooftf.http.monitor.serializable.ChunkSwitch
import com.ooftf.http.monitor.ui.SettingActivity
import com.readystatesoftware.chuck.Chuck
import com.readystatesoftware.chuck.internal.ui.MainActivity
import okhttp3.OkHttpClient

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/6/4
 */
object Monitor {
    var isInit = false
    fun init() {
        if (isInit) {
            return
        } else {
            isInit = true
        }
        AppHolder.app.registerActivityLifecycleCallbacks(
            ActivityLifecycleCallbacksArmor(
                onActivityPostCreated = { activity, bundle ->
                    if (activity is MainActivity) {
                        ChunkSwitch.bind(activity.findViewById<SwitchCompat>(R.id.switchView))
                    }
                })
        )
    }

    fun getNetMockIntent(context: Context) =
        Intent(context, SettingActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    fun getNetLogIntent(context: Context) = Chuck.getLaunchIntent(context)

}

fun OkHttpClient.Builder.applyMonitor(): OkHttpClient.Builder {
    addInterceptor(ReviseInterceptor())
    addInterceptor(ChuckInterceptorPro())
    return this
}