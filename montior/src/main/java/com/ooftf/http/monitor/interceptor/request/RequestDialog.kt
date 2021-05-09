package com.ooftf.http.monitor.interceptor.request

import android.content.Context
import android.util.Log
import android.widget.SeekBar
import com.ooftf.http.monitor.R
import com.ooftf.http.monitor.databinding.MontiorDialogRequestBinding
import com.ooftf.http.monitor.serializable.RequestUrls
import com.ooftf.http.monitor.util.CopyUtil
import com.ooftf.master.widget.dialog.ui.BaseDialog
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
    val binding = MontiorDialogRequestBinding.inflate(layoutInflater)
    init {

        setContentView(binding.root)
        setWidthMatchParent()
        setHeightMatchParent()
        setImmersion()
        binding.ok.setOnClickListener {
            dismiss()
        }
        setCanceledOnTouchOutside(false)
        binding.SeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val size = 12 + (progress.toFloat() / 10)
                binding.body.textSize = size
                binding.url.textSize = size
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })


        binding.urlTitle.setOnLongClickListener {
            CopyUtil.copyText("URL", binding.url.text)
            true
        }

        binding.requestTitle.setOnLongClickListener {
            CopyUtil.copyText("REQUEST_BODY", binding.body.text)
            true
        }


        binding.methodRecyclerView.adapter = methodAdapter
        binding.headerRecyclerView.adapter = headerAdapter
        binding.addHeader.setOnClickListener {
            headerAdapter.header.add(KV("", ""))
            headerAdapter.notifyDataSetChanged()
        }

        binding.switchView.setOnClickListener {
            if (binding.switchView.isChecked) {
                RequestUrls.get().add(binding.title.text.toString())
                RequestUrls.sync()
            } else {
                RequestUrls.get().remove(binding.title.text.toString())
                RequestUrls.sync()
            }
        }
    }

    fun setTitle(s: String) {
        binding.title.text = s
    }

    fun setMethod(method: String) {
        methodAdapter.current = method
        methodAdapter.notifyDataSetChanged()
    }

    fun getMethod(): String {
        return methodAdapter.current
    }

    fun setRequestBody(bodyString: String) {
        binding.body.setText(bodyString)
    }

    fun getRequestBody(): String {
        return binding.body.text.toString()
    }

    fun setUrl(urlString: String) {
        binding.url.setText(urlString)
    }

    fun getUrl(): String {
        return binding.url.text.toString()
    }

    fun setHeader(headers: Headers) {
        headers.forEach {
            Log.e("headers", "${it.first}::${it.second}")
            headerAdapter.header.add(KV(it.first, it.second))
        }
        headerAdapter.notifyDataSetChanged()
    }

    fun setRequestEnabled(enabled: Boolean) {
        binding.body.isEnabled = enabled
    }

    fun getHeader(): Headers {
        val build = Headers.Builder()
        headerAdapter.header.forEach {
            build.add(it.key, it.value)
        }
        return build.build()
    }

    fun setRequestType(requestBodyType: String) {
        binding.requestTitle.text = "REQUEST_BODY（${requestBodyType}）"
    }

}