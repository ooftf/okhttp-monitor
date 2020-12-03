package com.ooftf.http.monitor

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.widget.Switch
import androidx.appcompat.widget.SwitchCompat
import com.lzf.easyfloat.EasyFloat
import com.lzf.easyfloat.enums.ShowPattern
import com.ooftf.basic.AppHolder
import com.ooftf.basic.armor.ActivityLifecycleCallbacksArmor
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
                        Log.e("activity","activity")
                        val float = EasyFloat.getFloatView(activity)
                        if(float == null){
                            EasyFloat
                                .with(activity)
                                .setLayout(R.layout.montior_ooftf_chuck_switch) {
                                    ChunkSwitch.bind(it.findViewById<SwitchCompat>(R.id.switchView))
                                }
                                .setGravity(Gravity.END or Gravity.TOP, 0, 200)
                                .setDragEnable(false)
                                .setShowPattern(ShowPattern.CURRENT_ACTIVITY)
                                .show()
                        }else{
                            EasyFloat.show(activity)
                        }

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