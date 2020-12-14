package com.ooftf.http.monitor.interceptor

import com.ooftf.http.monitor.interceptor.request.RequestHandler
import com.ooftf.http.monitor.interceptor.request.RequestWrapper
import com.ooftf.http.monitor.serializable.AllUrls
import com.ooftf.http.monitor.serializable.ReviseSwitch
import okhttp3.Interceptor
import okhttp3.Response


/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/6/3
 */
class ReviseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!ReviseSwitch.get()) {
            return chain.proceed(chain.request())
        }
        addUrl(chain.request().url.encodedPath)
        val response = if (RequestHandler.isNeedHandler(chain.request())) {
            val requestWrapper = RequestWrapper(chain.request())
            RequestHandler.handler(requestWrapper)
            chain.proceed(requestWrapper.request())
        } else {
            chain.proceed(chain.request())
        }

        if (!ResponseHandler.isNeedHandler(response)) {
            return response
        }
        val rw = ResponseWrapper(response)
        ResponseHandler.handleResponse(rw)
        return rw.processResponse()
    }


    fun addUrl(s: String) {
        AllUrls.get().remove(s)
        AllUrls.get().add(s)
        AllUrls.sync()
    }

}