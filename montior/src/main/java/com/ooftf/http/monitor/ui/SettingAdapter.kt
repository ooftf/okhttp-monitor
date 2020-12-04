package com.ooftf.http.monitor.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.ooftf.basic.utils.getActivity
import com.ooftf.http.monitor.serializable.AllUrls
import com.ooftf.http.monitor.serializable.InterceptUrls
import com.ooftf.http.monitor.R
import com.ooftf.http.monitor.serializable.ReviseSwitch
import kotlinx.android.synthetic.main.montior_activity_setting.*

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/6/4
 */
class SettingAdapter : RecyclerView.Adapter<SettingAdapter.ViewHolder>() {
    var inflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        return ViewHolder(inflater!!.inflate(R.layout.montior_item, parent, false))
    }

    override fun getItemCount(): Int {
        return AllUrls.get().size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e("onBindViewHolder", "${AllUrls.get().size}::$position")
        val item = AllUrls.get()[position]
        holder.checkBox.text = item
        holder.checkBox.isChecked = InterceptUrls.get().contains(item)
        holder.checkBox.setOnClickListener {
            val url = holder.checkBox.text.toString()
            if (holder.checkBox.isChecked) {
                if (!InterceptUrls.get().contains(url)) {
                    InterceptUrls.get().add(url)
                    InterceptUrls.sync()
                }
                if (!ReviseSwitch.get()) {
                    (it.context.getActivity() as? SettingActivity)?.let {
                        it.switchView.performClick()
                    }
                }
            } else {
                InterceptUrls.get().remove(url)
                InterceptUrls.sync()
            }
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox = itemView.findViewById<SwitchCompat>(R.id.checkbox)
    }
}