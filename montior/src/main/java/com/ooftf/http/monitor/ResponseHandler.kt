package com.ooftf.http.monitor

import android.app.Activity
import android.os.Handler
import android.os.Looper
import com.ooftf.http.monitor.ui.ResponseDialog
import com.readystatesoftware.chuck.internal.support.FormatUtils

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/6/3
 */
object ResponseHandler {

    val allUrl = ArrayList<String>()
    val interceptUrls = hashSetOf<String>()
    val disk = DiskObject<ArrayList<String>>()

    init {
        disk.get()?.let {
            allUrl.addAll(it)
        }
    }

    fun addUrl(s: String) {
        if (allUrl.firstOrNull() == s) return
        allUrl.remove(s)
        allUrl.add(0, s)
        disk.put(allUrl)
    }

    fun handleResponse(rw: ReviseInterceptor.ResponseWrapper) {
        if (!interceptUrls.contains(rw.response.request().url().encodedPath())) {
            rw.process()
            return
        }
        val body = rw.json ?: ""
        runOnUiThread(Runnable {
            val a: Activity = Monitor.monitorProvider?.getTopActivity() ?: return@Runnable
            try {
                val dialog = ResponseDialog(a)
                dialog.setUrl(rw.response.request().url().toString())
                dialog.setBody(FormatUtils.formatJson(body))
                dialog.setParam(rw.getRequestBodyString())
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