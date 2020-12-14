package com.ooftf.http.monitor.interceptor

import android.content.Context
import android.widget.SeekBar
import com.ooftf.http.monitor.R
import com.ooftf.http.monitor.serializable.ResponseUrls
import com.ooftf.http.monitor.util.CopyUtil
import com.ooftf.master.widget.dialog.ui.BaseDialog
import kotlinx.android.synthetic.main.montior_dialog_response.*


/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/6/4
 */
class ResponseDialog(context: Context) :
    BaseDialog(context) {

    init {
        setContentView(R.layout.montior_dialog_response)
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
                param.textSize = size
                headers.textSize = size

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
        headerTitle.setOnLongClickListener {
            CopyUtil.copyText("HEADER", headers.text)
            true
        }
        paramTitle.setOnLongClickListener {
            CopyUtil.copyText("PARAM", param.text)
            true
        }
        responseTitle.setOnLongClickListener {
            CopyUtil.copyText("RESPONSE", body.text)
            true
        }

        switchView.setOnClickListener {
            if (switchView.isChecked) {
                ResponseUrls.get().add(title.text.toString())
                ResponseUrls.sync()
            } else {
                ResponseUrls.get().remove(title.text.toString())
                ResponseUrls.sync()
            }
        }
    }

    fun setUrl(s: String) {
        url.text = s
    }

    fun setBody(s: String) {
        body.setText(s)
    }

    fun setParam(s: String) {
        param.text = s
    }

    fun setHeader(s: String) {
        headers.text = s
    }

    fun setTitle(s: String) {
        title.text = s
    }

    fun getBody() = body.text.toString()


}