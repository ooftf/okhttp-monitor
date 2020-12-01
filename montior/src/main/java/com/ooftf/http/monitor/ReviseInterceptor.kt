package com.ooftf.http.monitor

import android.util.Log
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONObject


/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/6/3
 */
class ReviseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (Monitor.monitorProvider == null || Monitor.monitorProvider?.isMockNetData() == false) {
            return chain.proceed(chain.request())
        }
        Log.e("intercept", chain.request().url.encodedPath)
        ResponseHandler.addUrl(chain.request().url.encodedPath)
        val response = chain.proceed(chain.request())
        val rw =
            ResponseWrapper(
                response
            )
        ResponseHandler.handleResponse(rw)
        while (!rw.isProcess) {
            Thread.sleep(500)
        }
        return rw.processResponse()
    }


    class ResponseWrapper(var response: Response) {
        var isProcess = false
        val json by lazy {
            response.body?.string()
        }
        var newJson: String? = null

        fun process() {
            isProcess = true
        }

        fun getRequestBodyString(): String {
            val body = response.request.body
            return if (body is FormBody) {
                val json = JSONObject()
                for (i in 0 until body.size) {
                    json.put(body.name(i), body.value(i))
                }
                json.toString()
            } else {
                body.toString()
            }
        }

        fun getRequestHeaders(): String {
            return response.request.headers.toString()
        }

        fun getPath(): String {
            return response.request.url.encodedPath
        }

        fun getMethod(): String {
            return response.request.method
        }

        fun processResponse(): Response {
            if (response.body == null) {
                return response
            }
            if (response.body!!.source().isOpen) {
                return response
            }

            val myBody: ResponseBody =
                ResponseBody.create(response.body?.contentType(), newJson ?: json ?: "")
            return response.newBuilder().body(myBody).build()

        }
    }
}