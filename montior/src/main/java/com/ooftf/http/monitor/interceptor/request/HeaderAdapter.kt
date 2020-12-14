package com.ooftf.http.monitor.interceptor.request

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.ooftf.basic.utils.inflate
import com.ooftf.http.monitor.R

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/12/14
 */
class HeaderAdapter : RecyclerView.Adapter<HeaderViewHolder>() {
    val header = arrayListOf<KV>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        return HeaderViewHolder(parent.inflate(R.layout.montior_ooftf_header_item, parent)).apply {
            key.addTextChangedListener {
                header[adapterPosition].key = key.text.toString()

            }
            value.addTextChangedListener {
                header[adapterPosition].value = value.text.toString()
            }
            delete.setOnClickListener {
                header.removeAt(adapterPosition)
                notifyDataSetChanged()
            }
        }
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {

        /*header[holder.oldPosition].let {
            it.key = holder.key.text.toString()
            it.value = holder.value.text.toString()
        }*/

        header[position].let {
            holder.key.text = it.key
            holder.value.text = it.value
        }

    }

    override fun getItemCount(): Int {
        return header.size
    }
}

class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val key = itemView.findViewById<TextView>(R.id.key)
    val value = itemView.findViewById<TextView>(R.id.value)
    val delete = itemView.findViewById<TextView>(R.id.delete)
}

class KV(var key: String, var value: String)