package com.ooftf.http.monitor.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.ooftf.basic.AppHolder

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/12/14
 */
object CopyUtil {
    /**
     * 复制文本到剪贴板
     *
     * @param text 文本
     */
    fun copyText(label: String, text: CharSequence?) {
        val cm: ClipboardManager = AppHolder.app
            .getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.setPrimaryClip(ClipData.newPlainText(label, text))
        Toast.makeText(AppHolder.app, "${label}已复制到粘贴板", Toast.LENGTH_SHORT).show()
    }
}