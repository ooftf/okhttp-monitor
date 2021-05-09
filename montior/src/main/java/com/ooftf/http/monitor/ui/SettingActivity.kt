package com.ooftf.http.monitor.ui

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.ooftf.http.monitor.databinding.MontiorActivitySettingBinding
import com.ooftf.http.monitor.serializable.ResponseUrls
import com.ooftf.http.monitor.serializable.ReviseSwitch

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/6/4
 */
class SettingActivity : Activity() {
    val binding by lazy {
        MontiorActivitySettingBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
            )
        }
        setContentView(binding.root)
        binding.recyclerView.adapter = SettingAdapter()
        ReviseSwitch.bind(binding.switchView) { buttonView, isChecked ->
            if (!isChecked) {
                ResponseUrls.clear()
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
        }
    }
}