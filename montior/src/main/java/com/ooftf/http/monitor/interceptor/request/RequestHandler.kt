package com.ooftf.http.monitor.interceptor.request

import android.app.Activity
import com.ooftf.basic.engine.ActivityManager
import com.ooftf.http.monitor.interceptor.ResponseHandler
import com.ooftf.http.monitor.serializable.RequestUrls
import okhttp3.Request
import okhttp3.internal.notify
import okhttp3.internal.wait

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/12/14
 */
object RequestHandler {

    fun isNeedHandler(request: Request): Boolean {
        return RequestUrls.get().contains(request.url.encodedPath)
    }

    fun handler(rw: RequestWrapper) {
        ResponseHandler.runOnUiThread(Runnable {
            val a: Activity = ActivityManager.getTopActivity() ?: return@Runnable
            try {
                val dialog = RequestDialog(a)
                dialog.setUrl(rw.getUrl())
                dialog.setHeader(rw.getRequestHeader())
                dialog.setMethod(rw.getMethod())
                dialog.setTitle(rw.request.url.encodedPath)
                dialog.setOnDismissListener {
                    rw.buildNewRequest(
                        dialog.getMethod(),
                        dialog.getUrl(),
                        dialog.getHeader(),
                        dialog.getRequestBody()
                    )
                    synchronized(rw) {
                        rw.notify()
                    }

                }
                dialog.setRequestEnabled(rw.bodyIsCanModify)
                dialog.setRequestType(rw.getRequestBodyType())
                dialog.setRequestBody(rw.getRequestBody())
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
}