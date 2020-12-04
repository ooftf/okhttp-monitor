package com.ooftf.http.monitor.interceptor

import com.ooftf.basic.AppHolder
import com.ooftf.http.monitor.serializable.ChunkSwitch
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Interceptor
import okhttp3.Response

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/6/4
 */
class ChuckInterceptorPro : Interceptor {
    private val interceptor = ChuckInterceptor(AppHolder.app)
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (ChunkSwitch.get()) {
            interceptor.intercept(chain)
        } else {
            chain.proceed(chain.request())
        }

    }
}