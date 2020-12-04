package com.ooftf.http.monitor.interceptor

import android.app.Activity
import android.os.Handler
import android.os.Looper
import com.ooftf.basic.engine.ActivityManager
import com.ooftf.http.monitor.serializable.AllUrls
import com.ooftf.http.monitor.serializable.InterceptUrls
import com.readystatesoftware.chuck.internal.support.FormatUtils
import java.util.*

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/6/3
 */
object ResponseHandler {


    fun addUrl(s: String) {
        if (AllUrls.get().firstOrNull() == s) return
        AllUrls.get().remove(s)
        AllUrls.get().add(0, s)
        AllUrls.sync()
    }

    fun handleResponse(rw: ReviseInterceptor.ResponseWrapper) {
        if (!InterceptUrls.get().contains(rw.response.request.url.encodedPath)) {
            rw.process()
            return
        }
        val body = rw.json ?: ""
        runOnUiThread(Runnable {
            val a: Activity = ActivityManager.getTopActivity() ?: return@Runnable
            try {
                val dialog = ResponseDialog(a)
                dialog.setUrl(rw.response.request.url.toString())
                dialog.setBody(FormatUtils.formatJson(body))
                dialog.setParam(rw.getRequestBodyString())
                dialog.setHeader(rw.getRequestHeaders())
                dialog.setTitle("${rw.getMethod()} : ${rw.getPath()}")
                dialog.setOnDismissListener {
                    rw.newJson = dialog.getBody()
                    rw.process()
                }
                dialog.show()
            } catch (e: Throwable) {
                e.printStackTrace()
                rw.process()
            }
        })


    }

    private val mainHandler = Handler(Looper.getMainLooper())
    fun runOnUiThread(runnable: Runnable) {
        if (isMainThread()) {
            runnable.run()
        } else {
            mainHandler.post(runnable)
        }
    }

    fun isMainThread(): Boolean {
        return Thread.currentThread() === Looper.getMainLooper().thread
    }
}