package com.ooftf.http.monitor.interceptor

import okhttp3.FormBody
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONObject

class ResponseWrapper(var response: Response) {
    val json by lazy {
        response.body?.string()
    }
    var newJson: String? = null

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