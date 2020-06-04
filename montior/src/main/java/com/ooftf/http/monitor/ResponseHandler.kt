package com.ooftf.http.monitor

import android.app.Activity
import android.os.Handler
import android.os.Looper
import com.ooftf.http.monitor.ui.ResponseDialog

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/6/3
 */
object ResponseHandler {

    val allUrl = ArrayList<String>()
    val interceptUrls = hashSetOf<String>()


    fun addUrl(s: String) {
        if (allUrl.contains(s)) return
        allUrl.add(s)
    }

    fun handlerResponse(rw: ReviseInterceptor.ResponseWrapper) {
        if (!interceptUrls.contains(rw.response.request.url.toString())) {
            rw.isProcess = true
            return
        }
        runOnUiThread(Runnable {
            val a: Activity = Monitor.m?.getTopActivity() ?: return@Runnable
            try {
                var dialog = ResponseDialog(a)
                dialog.setUrl(rw.response.request.url.toString())
                dialog.setBody(rw.json ?: "")
                dialog.setOnDismissListener {
                    rw.newJson = dialog.getBody()
                    rw.process()
                }
                dialog.show()
            } catch (e: Throwable) {
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