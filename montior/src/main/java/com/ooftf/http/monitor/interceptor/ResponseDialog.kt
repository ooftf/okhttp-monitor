package com.ooftf.http.monitor.interceptor

import android.content.Context
import android.widget.SeekBar
import com.ooftf.http.monitor.R
import com.ooftf.http.monitor.databinding.MontiorDialogResponseBinding
import com.ooftf.http.monitor.serializable.ResponseUrls
import com.ooftf.http.monitor.util.CopyUtil
import com.ooftf.master.widget.dialog.ui.BaseDialog


/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/6/4
 */
class ResponseDialog(context: Context) :
    BaseDialog(context) {
    val binding = MontiorDialogResponseBinding.inflate(layoutInflater)
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
                binding.param.textSize = size
                binding.headers.textSize = size

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
        binding.headerTitle.setOnLongClickListener {
            CopyUtil.copyText("HEADER", binding.headers.text)
            true
        }
        binding.paramTitle.setOnLongClickListener {
            CopyUtil.copyText("PARAM", binding.param.text)
            true
        }
        binding.responseTitle.setOnLongClickListener {
            CopyUtil.copyText("RESPONSE", binding.body.text)
            true
        }

        binding.switchView.setOnClickListener {
            if (binding.switchView.isChecked) {
                ResponseUrls.get().add(binding.title.text.toString())
                ResponseUrls.sync()
            } else {
                ResponseUrls.get().remove(binding.title.text.toString())
                ResponseUrls.sync()
            }
        }
    }

    fun setUrl(s: String) {
        binding.url.text = s
    }

    fun setBody(s: String) {
        binding.body.setText(s)
    }

    fun setParam(s: String) {
        binding.param.text = s
    }

    fun setHeader(s: String) {
        binding.headers.text = s
    }

    fun setTitle(s: String) {
        binding.title.text = s
    }

    fun getBody() = binding.body.text.toString()


}