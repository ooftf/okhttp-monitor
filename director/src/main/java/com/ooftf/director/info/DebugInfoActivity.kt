package com.ooftf.director.info

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ooftf.director.Item
import com.ooftf.director.R
import com.ooftf.director.databinding.DirectorOoftfActivityCommonListBinding

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/5/21
 */
class DebugInfoActivity :
    AppCompatActivity() {
    val viewModel :DebugInfoViewModel by viewModels()
    lateinit var binding: DirectorOoftfActivityCommonListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.director_ooftf_activity_common_list)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.items.add(Item("定位权限", isPermissions().toString()))
    }

    fun isPermissions(): Boolean {
        return isGranted(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                || isGranted(this, Manifest.permission.ACCESS_FINE_LOCATION)
    }

    fun isGranted(activity: Activity, permission: String): Boolean {
        return !this.isMarshmallow() || activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    fun isMarshmallow(): Boolean {
        return Build.VERSION.SDK_INT >= 23
    }
}