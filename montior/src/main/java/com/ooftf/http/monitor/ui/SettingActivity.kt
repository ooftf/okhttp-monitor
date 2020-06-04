package com.ooftf.http.monitor.ui

import android.app.Activity
import android.os.Bundle
import com.ooftf.http.monitor.R
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
        setContentView(R.layout.montior_activity_setting)
        recyclerView.adapter = SettingAdapter()
    }
}