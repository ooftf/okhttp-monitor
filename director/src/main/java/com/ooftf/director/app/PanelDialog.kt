package com.ooftf.director.app

import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.didichuxing.doraemonkit.DoraemonKit
import com.lzf.easyfloat.EasyFloat
import com.lzf.easyfloat.enums.ShowPattern
import com.lzf.easyfloat.enums.SidePattern
import com.lzf.easyfloat.interfaces.OnInvokeView
import com.lzf.easyfloat.interfaces.OnPermissionResult
import com.lzf.easyfloat.permission.PermissionUtils
import com.ooftf.basic.AppHolder
import com.ooftf.basic.engine.ActivityManager
import com.ooftf.basic.utils.getActivity
import com.ooftf.basic.utils.getCurrentFragment
import com.ooftf.basic.utils.toast
import com.ooftf.director.*
import com.ooftf.director.entrance.DebugEntranceActivity
import com.ooftf.http.monitor.Monitor
import com.ooftf.master.widget.dialog.inteface.ListDialogInterface
import com.ooftf.master.widget.dialog.ui.ListDialog
import com.ooftf.master.widget.dialog.utils.DialogUtil

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/5/21
 */
object PanelDialog {
    internal val items by lazy {
        ArrayList<Item>().apply {
            add(Item("当前Activity名称") { toast(getTopActivityName(), duration = Toast.LENGTH_LONG) })
            add(Item("当前Fragment名称") { toast(getCurrentFragmentName(), duration = Toast.LENGTH_LONG) })
            add(Item("显示当前LOG") {
                showLogDialog()
            })
            add(Item("调试功能界面") {
                AppHolder.app.startActivity(Intent(AppHolder.app, DebugEntranceActivity::class.java))
            })
            add(Item("网络接口日志") {
                AppHolder.app.startActivity(
                        Monitor.getNetLogIntent(
                                AppHolder.app
                        )
                )
            })
            add(Item("网络接口Mock") {
                AppHolder.app.startActivity(
                        Monitor.getNetMockIntent(
                                AppHolder.app
                        )
                )
            })
            add(Item("滴滴调试面板") {
                DoraemonKit.showToolPanel()
            })
            addAll(Director.customPanelItems)
        }
    }

    private fun getTopActivityName(): String {
        val a = ActivityManager.getTopActivity()
        return if (a == null) {
            ""
        } else {
            a::class.java.name
        }
    }

    private fun getCurrentFragmentName(): String {
        val a = ActivityManager.getTopActivity()
        val currentFragment = a?.getCurrentFragment()
        return if (currentFragment == null) {
            "not find fragment"
        } else {
            currentFragment::class.java.name
        }
    }


    fun showFeaturesDialog() {
        ActivityManager.getTopActivity()?.let { topActivity ->
            if (DialogUtil.isHasDialog(topActivity)) {
                return
            }
            ListDialog(topActivity)
                    .setList(items.map { it.key.value })
                    .setShowCancel(true)
                    .setOnItemClickListener { dialog: ListDialogInterface, position: Int, item: String? ->
                        dialog.dismiss()
                        items[position].listener?.invoke()
                    }
                    .show_()
        }

    }

    fun openShowFloat(v: View) {
        if (PermissionUtils.checkPermission(AppHolder.app)) {
            ShowEntranceSwitch.set(true)
            showFloat(v.context)
        } else {
            v.context.getActivity()?.let {
                PermissionUtils.requestPermission(it, object : OnPermissionResult {
                    override fun permissionResult(isOpen: Boolean) {
                        if (isOpen) {
                            ShowEntranceSwitch.set(true)
                            showFloat(v.context)
                        } else {
                            toast("打开悬浮窗权限失败")
                        }
                    }
                })
            }
        }
    }


    fun showFloat(context: Context) {
        val appFloatView = EasyFloat.getAppFloatView(tag)
        if (appFloatView == null) {
            EasyFloat
                    .with(context)
                    .setLayout(R.layout.director_ooftf_float_view, OnInvokeView {
                        it.setOnClickListener {
                            showFeaturesDialog()
                        }
                    })
                    .setSidePattern(SidePattern.DEFAULT)
                    .setShowPattern(ShowPattern.FOREGROUND)
                    .setTag(tag)
                    .setGravity(Gravity.END or Gravity.BOTTOM, -100, -260)
                    .setMatchParent(widthMatch = false, heightMatch = false)
                    .show()
        } else {
            EasyFloat.showAppFloat(tag)
        }
    }

    private fun showLogDialog() {
        ActivityManager.getTopActivity()?.let {
            LogDialog(it).apply {
                setData(LogKnife.queue)
                val listener = { item: LogParser ->
                    data.add(item)
                    notifyItemInserted(0)
                }
                LogKnife.registerDataChange(listener)
                setOnDismissListener {
                    LogKnife.unregisterDataChange(listener)
                }
            }.show()
        }
    }

    val tag = "testFloat"

}