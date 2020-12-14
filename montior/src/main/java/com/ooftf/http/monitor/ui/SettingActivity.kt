package com.ooftf.http.monitor.ui

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.ooftf.http.monitor.R
import com.ooftf.http.monitor.serializable.ResponseUrls
import com.ooftf.http.monitor.serializable.ReviseSwitch
import kotlinx.android.synthetic.main.montior_activity_setting.*

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/6/4
 */
class SettingActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        )
        setContentView(R.layout.montior_activity_setting)
        recyclerView.adapter = SettingAdapter()
        ReviseSwitch.bind(switchView) { buttonView, isChecked ->
            if (!isChecked) {
                ResponseUrls.clear()
                recyclerView.adapter?.notifyDataSetChanged()
            }
        }
    }
}