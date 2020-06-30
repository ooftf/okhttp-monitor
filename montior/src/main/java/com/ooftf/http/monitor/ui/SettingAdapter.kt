package com.ooftf.http.monitor.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Switch
import androidx.recyclerview.widget.RecyclerView
import com.ooftf.http.monitor.R
import com.ooftf.http.monitor.ResponseHandler

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
        return ResponseHandler.allUrl.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e("onBindViewHolder","${ResponseHandler.allUrl.size}::$position")
        val item = ResponseHandler.allUrl[position]
        holder.checkBox.text = item
        holder.checkBox.isChecked = ResponseHandler.interceptUrls.contains(item)
        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            val url = buttonView.text.toString()
            if (isChecked) {
                if (!ResponseHandler.interceptUrls.contains(url)) {
                    ResponseHandler.interceptUrls.add(url)
                }
            } else {
                ResponseHandler.interceptUrls.remove(url)
            }
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox = itemView.findViewById<Switch>(R.id.checkbox)
    }
}