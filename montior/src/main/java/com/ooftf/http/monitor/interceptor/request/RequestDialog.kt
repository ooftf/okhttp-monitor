package com.ooftf.http.monitor.interceptor.request

import android.content.Context
import android.util.Log
import android.widget.SeekBar
import com.ooftf.http.monitor.R
import com.ooftf.http.monitor.serializable.RequestUrls
import com.ooftf.http.monitor.util.CopyUtil
import com.ooftf.master.widget.dialog.ui.BaseDialog
import kotlinx.android.synthetic.main.montior_dialog_request.*
import okhttp3.Headers

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/12/14
 */
class RequestDialog(context: Context) : BaseDialog(context) {
    val methodAdapter = MethodAdapter()
    val headerAdapter = HeaderAdapter()

    init {
        setContentView(R.layout.montior_dialog_request)
        setWidthMatchParent()
        setHeightMatchParent()
        setImmersion()
        ok.setOnClickListener {
            dismiss()
        }
        setCanceledOnTouchOutside(false)
        SeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val size = 12 + (progress.toFloat() / 10)
                body.textSize = size
                url.textSize = size
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })


        urlTitle.setOnLongClickListener {
            CopyUtil.copyText("URL", url.text)
            true
        }

        requestTitle.setOnLongClickListener {
            CopyUtil.copyText("REQUEST_BODY", body.text)
            true
        }


        methodRecyclerView.adapter = methodAdapter
        headerRecyclerView.adapter = headerAdapter
        addHeader.setOnClickListener {
            headerAdapter.header.add(KV("", ""))
            headerAdapter.notifyDataSetChanged()
        }

        switchView.setOnClickListener {
            if (switchView.isChecked) {
                RequestUrls.get().add(title.text.toString())
                RequestUrls.sync()
            } else {
                RequestUrls.get().remove(title.text.toString())
                RequestUrls.sync()
            }
        }
    }

    fun setTitle(s: String) {
        title.text = s
    }

    fun setMethod(method: String) {
        methodAdapter.current = method
        methodAdapter.notifyDataSetChanged()
    }

    fun getMethod(): String {
        return methodAdapter.current
    }

    fun setRequestBody(bodyString: String) {
        body.setText(bodyString)
    }

    fun getRequestBody(): String {
        return body.text.toString()
    }

    fun setUrl(urlString: String) {
        url.setText(urlString)
    }

    fun getUrl(): String {
        return url.text.toString()
    }

    fun setHeader(headers: Headers) {
        headers.forEach {
            Log.e("headers", "${it.first}::${it.second}")
            headerAdapter.header.add(KV(it.first, it.second))
        }
        headerAdapter.notifyDataSetChanged()
    }

    fun setRequestEnabled(enabled: Boolean) {
        body.isEnabled = enabled
    }

    fun getHeader(): Headers {
        val build = Headers.Builder()
        headerAdapter.header.forEach {
            build.add(it.key, it.value)
        }
        return build.build()
    }

    fun setRequestType(requestBodyType: String) {
        requestTitle.text = "REQUEST_BODY（${requestBodyType}）"
    }

}