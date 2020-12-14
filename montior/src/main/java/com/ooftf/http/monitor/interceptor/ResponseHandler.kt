package com.ooftf.http.monitor.interceptor

import android.app.Activity
import android.os.Handler
import android.os.Looper
import com.ooftf.basic.engine.ActivityManager
import com.ooftf.http.monitor.serializable.ResponseUrls
import com.readystatesoftware.chuck.internal.support.FormatUtils
import okhttp3.Response
import okhttp3.internal.notify
import okhttp3.internal.wait
import java.util.*

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/6/3
 */
object ResponseHandler {


    fun isNeedHandler(response: Response): Boolean {
        return ResponseUrls.get().contains(response.request.url.encodedPath)
    }

    fun handleResponse(rw: ResponseWrapper) {
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
                    synchronized(rw) {
                        rw.notify()
                    }
                }
                dialog.show()
            } catch (e: Throwable) {
                e.printStackTrace()
                synchronized(rw) {
                    rw.notify()
                }
            }
        })
        synchronized(rw) {
            rw.wait()
        }
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