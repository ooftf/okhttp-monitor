package com.ooftf.http.monitor.interceptor.request

import okhttp3.Headers
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.Buffer
import okio.BufferedSource
import okio.GzipSource
import okio.buffer
import java.io.EOFException
import java.nio.charset.Charset

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/12/14
 */
class RequestWrapper(val request: Request) {
    fun getUrl(): String {
        return request.url.toString()
    }

    fun getRequestHeader(): Headers {
        return request.headers
    }

    fun getMethod(): String {
        return request.method
    }

    fun getRequestBodyType(): String {
        return request.body?.javaClass?.simpleName ?: ""
    }

    var bodyIsCanModify = true
    var originalRequestBodyString = ""
    fun getRequestBody(): String {
        originalRequestBodyString = getRequestBody_()
        return originalRequestBodyString
    }

    fun getRequestBody_(): String {
        val body = request.body ?: return ""
        val source: BufferedSource = getNativeSource(Buffer(), bodyGzipped(request.headers))
        val buffer = source.buffer
        body.writeTo(buffer)
        var charset = UTF8
        val contentType: MediaType? = body.contentType()
        if (contentType != null) {
            charset = contentType.charset(UTF8)
        }
        return if (isPlaintext(buffer)) {
            readFromBuffer(buffer, charset)
        } else {
            bodyIsCanModify = false
            "RequestBody con't parse into text"
        }
    }

    fun request(): Request {
        return return newRequest ?: request
    }

    private fun readFromBuffer(buffer: Buffer, charset: Charset): String {
        val bufferSize = buffer.size
        val maxBytes = Math.min(bufferSize, maxContentLength)
        var body = ""
        try {
            body = buffer.readString(maxBytes, charset)
        } catch (e: EOFException) {
            bodyIsCanModify = false
            body += "\\n\\n--- Unexpected end of content ---"
        }
        if (bufferSize > maxContentLength) {
            bodyIsCanModify = false
            body += "\\n\\n--- Content truncated ---"
        }
        return body
    }

    private fun isPlaintext(buffer: Buffer): Boolean {
        return try {
            val prefix = Buffer()
            val byteCount = if (buffer.size < 64) buffer.size else 64
            buffer.copyTo(prefix, 0, byteCount)
            for (i in 0..15) {
                if (prefix.exhausted()) {
                    break
                }
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false
                }
            }
            true
        } catch (e: EOFException) {
            false // Truncated UTF-8 sequence.
        }
    }

    private fun bodyGzipped(headers: Headers): Boolean {
        val contentEncoding = headers["Content-Encoding"]
        return "gzip".equals(contentEncoding, ignoreCase = true)
    }

    private fun getNativeSource(input: BufferedSource, isGzipped: Boolean): BufferedSource {
        return if (isGzipped) {
            val source = GzipSource(input)
            source.buffer()
        } else {
            input
        }
    }

    fun buildNewRequest(method: String, url: String, header: Headers, requestBody: String) {
        var newBody: RequestBody? = if (originalRequestBodyString != requestBody) {
            requestBody.toRequestBody(request.body?.contentType())
        } else {
            request.body
        }
        newRequest = request.newBuilder().headers(header).url(url).method(method, newBody).build()
    }

    var newRequest: Request? = null

    companion object {
        private val UTF8 = Charset.forName("UTF-8")
        private const val maxContentLength = 250000L
    }

}