package com.ooftf.http.monitor.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.ooftf.basic.utils.getActivity
import com.ooftf.http.monitor.R
import com.ooftf.http.monitor.serializable.AllUrls
import com.ooftf.http.monitor.serializable.RequestUrls
import com.ooftf.http.monitor.serializable.ResponseUrls
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
        holder.text.text = item
        holder.responseSwitch.isChecked = ResponseUrls.get().contains(item)
        holder.responseSwitch.setOnClickListener {
            val url = holder.text.toString()
            if (holder.responseSwitch.isChecked) {
                if (!ResponseUrls.get().contains(url)) {
                    ResponseUrls.get().add(url)
                    ResponseUrls.sync()
                }
                if (!ReviseSwitch.get()) {
                    (it.context.getActivity() as? SettingActivity)?.let {
                        it.switchView.performClick()
                    }
                }
            } else {
                ResponseUrls.get().remove(url)
                ResponseUrls.sync()
            }
        }

        holder.request.isChecked = RequestUrls.get().contains(item)
        holder.request.setOnClickListener {
            val url = holder.text.text.toString()
            if (holder.request.isChecked) {
                if (!RequestUrls.get().contains(url)) {
                    RequestUrls.get().add(url)
                    RequestUrls.sync()
                }
                if (!ReviseSwitch.get()) {
                    (it.context.getActivity() as? SettingActivity)?.let {
                        it.switchView.performClick()
                    }
                }
            } else {
                RequestUrls.get().remove(url)
                RequestUrls.sync()
            }
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val responseSwitch = itemView.findViewById<SwitchCompat>(R.id.responseSwitch)
        val text = itemView.findViewById<TextView>(R.id.text)
        val request = itemView.findViewById<SwitchCompat>(R.id.request)
    }
}