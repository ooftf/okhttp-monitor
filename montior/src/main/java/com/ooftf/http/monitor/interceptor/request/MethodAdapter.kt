package com.ooftf.http.monitor.interceptor.request

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ooftf.basic.utils.inflate
import com.ooftf.http.monitor.R

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/12/14
 */
class MethodAdapter : RecyclerView.Adapter<ViewHolder>() {
    var current = ""
    val data: List<String> = arrayListOf("GET", "POST", "PUT", "DELETE")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.montior_method_item, parent)).apply {
            textView.setOnClickListener {
                current = data[adapterPosition]
                notifyDataSetChanged()
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = data[position]
        holder.textView.isSelected = data[position] == current
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView = itemView as TextView
}