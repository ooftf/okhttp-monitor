package com.ooftf.director.entrance

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import com.lzf.easyfloat.EasyFloat
import com.ooftf.basic.AppHolder
import com.ooftf.director.AppUtils
import com.ooftf.director.CommonListViewModel
import com.ooftf.director.Item
import com.ooftf.director.R
import com.ooftf.director.app.Director
import com.ooftf.director.app.PanelDialog
import com.ooftf.director.app.ShowEntranceSwitch
import com.ooftf.director.info.DebugInfoActivity
import com.ooftf.http.monitor.Monitor

class DebugEntranceViewModel(application: Application) :
        CommonListViewModel<Item>(application) {

    init {
        title.postValue("调试功能列表")

        items.add(
                Item(
                        "调试信息"
                ) {
                    AppHolder.app.startActivity(Intent(AppHolder.app, DebugInfoActivity::class.java))
                }
        )

        items.add(Item("显示工具浮窗").apply {
            isChecked.value = isDevShow()
            val action = Observer<Boolean> {
                setDevShow(it)
                ShowEntranceSwitch.set(it)
            }
            isChecked.observeForever(action)
            doOnCleared {
                isChecked.removeObserver(action)
            }
        })

        items.add(
                Item(
                        "网络接口日志"
                ) {
                    AppHolder.app.startActivity(
                            Monitor.getNetLogIntent(
                                    AppHolder.app
                            )
                    )
                }
        )
        items.add(
                Item(
                        "网络数据Mock"
                ) {
                    AppHolder.app.startActivity(
                            Monitor.getNetMockIntent(
                                    AppHolder.app
                            )
                    )
                }
        )
        items.add(Item("重置应用（数据和权限）") {
            val am =
                    AppHolder.app.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            am.clearApplicationUserData()
        })
        items.add(Item("跳转设置界面") {
            AppUtils.launchAppDetailsSettings(com.ooftf.basic.engine.ActivityManager.getTopActivity(), AppHolder.app.packageName)
        })
        items.addAll(Director.customDebugItems)
    }

    override fun getItemLayout(): Int {
        return R.layout.director_ooftf_item
    }

    companion object {
        fun isDevShow(): Boolean {
            return EasyFloat.appFloatIsShow(PanelDialog.tag)
        }

        fun setDevShow(isChecked: Boolean) {
            if (isChecked) {
                EasyFloat.showAppFloat(PanelDialog.tag)
            } else {
                EasyFloat.hideAppFloat(PanelDialog.tag)
            }

        }
    }
}
