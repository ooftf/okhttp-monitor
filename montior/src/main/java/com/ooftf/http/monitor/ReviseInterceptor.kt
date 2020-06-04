package com.ooftf.http.monitor

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Response
import okhttp3.ResponseBody


/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/6/3
 */
class ReviseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        ResponseHandler.addUrl(chain.request().url.toString())
        val response = chain.proceed(chain.request())
        val rw =
            ResponseWrapper(
                response
            )
        ResponseHandler.handlerResponse(rw)
        while (!rw.isProcess) {
            Thread.sleep(100)
        }
        return rw.response
    }


    class ResponseWrapper(var response: Response) {
        var isProcess = false
        val json by lazy {
            response.body?.string()
        }

        var newJson: String? = null

        fun process() {
            isProcess = true
            val myBody: ResponseBody =
                ResponseBody.create("text/plain".toMediaType(), newJson ?: json ?: "")
            response = response.newBuilder().body(myBody).build()
        }
    }
}