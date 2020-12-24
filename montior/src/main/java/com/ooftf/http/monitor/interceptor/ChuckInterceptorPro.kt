package com.ooftf.http.monitor.interceptor

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.ooftf.basic.AppHolder
import com.ooftf.http.monitor.serializable.ChunkSwitch
import okhttp3.Interceptor
import okhttp3.Response

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/6/4
 */
class ChuckInterceptorPro : Interceptor {
    val chucker by lazy {
        // Create the Collector
        val chuckerCollector = ChuckerCollector(
            context = AppHolder.app,
            // Toggles visibility of the push notification
            showNotification = true,
            // Allows to customize the retention period of collected data
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )
        ChuckerInterceptor.Builder(AppHolder.app)
            .collector(chuckerCollector)
            .alwaysReadResponseBody(false)
            .maxContentLength(250_000L).build()
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return if (ChunkSwitch.get()) {
            chucker.intercept(chain)
        } else {
            chain.proceed(chain.request())
        }

    }
}