package com.ooftf.http.monitor.ui

import android.content.Context
import android.widget.SeekBar
import com.ooftf.http.monitor.R
import com.ooftf.master.widget.dialog.ui.BaseDialog
import kotlinx.android.synthetic.main.montior_dialog_response.*

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/6/4
 */
class ResponseDialog(context: Context) : BaseDialog(context) {

    init {
        setContentView(R.layout.montior_dialog_response)
        setWidthPercent(1f)
        setHeightPercent(1f)
        ok.setOnClickListener {
            dismiss()
        }
        setCanceledOnTouchOutside(false)
        SeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                body.textSize = progress.toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
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

    fun getBody() = body.text.toString()
}