package com.ooftf.http.monitor

import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Interceptor
import okhttp3.Response

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/6/4
 */
class LogViewInterceptor : Interceptor {
    private val interceptor = ChuckInterceptor(Monitor.monitorProvider?.getApplication())
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (Monitor.monitorProvider?.isInterceptLog() == true) {
            interceptor.intercept(chain)
        } else {
            chain.proceed(chain.request())
        }

    }
}