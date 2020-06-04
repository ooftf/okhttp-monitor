package com.ooftf.http.monitor.ui

import android.app.Dialog
import android.content.Context
import com.ooftf.http.monitor.R
import kotlinx.android.synthetic.main.montior_dialog_response.*

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/6/4
 */
class ResponseDialog(context: Context) : Dialog(context) {

    init {
        setContentView(R.layout.montior_dialog_response)
        ok.setOnClickListener {
            dismiss()
        }

    }

    fun setUrl(s: String) {
        url.text = s
    }

    fun setBody(s: String) {
        body.setText(s)
    }

    fun getBody() = body.text.toString()
}