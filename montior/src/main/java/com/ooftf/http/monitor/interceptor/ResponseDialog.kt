package com.ooftf.http.monitor.interceptor

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.Toast
import com.ooftf.http.monitor.R
import com.ooftf.master.widget.dialog.ui.BaseDialog
import kotlinx.android.synthetic.main.montior_dialog_response.*


/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/6/4
 */
class ResponseDialog(context: Context) :
    BaseDialog(context, R.style.master_DialogTheme_Transparent) {

    init {
        setContentView(R.layout.montior_dialog_response)
        setWidthMatchParent()
        setHeightMatchParent()
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

        /*window?.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )*/
        //需要设置这个才能设置状态栏和导航栏颜色
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = Color.TRANSPARENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        window?.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        )
        urlTitle.setOnLongClickListener {
            copyText("URL", url.text)
            true
        }
        headerTitle.setOnLongClickListener {
            copyText("HEADER", headers.text)
            true
        }
        paramTitle.setOnLongClickListener {
            copyText("PARAM", param.text)
            true
        }
        responseTitle.setOnLongClickListener {
            copyText("RESPONSE", body.text)
            true
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

    /**
     * 复制文本到剪贴板
     *
     * @param text 文本
     */
    fun copyText(label: String, text: CharSequence?) {
        val cm: ClipboardManager = context
            .getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.setPrimaryClip(ClipData.newPlainText(label, text))
        Toast.makeText(context, "${label}已复制到粘贴板", Toast.LENGTH_SHORT).show()
    }
}