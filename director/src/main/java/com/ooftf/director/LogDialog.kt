package com.ooftf.director

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ooftf.basic.AppHolder
import com.ooftf.master.widget.dialog.ui.BaseDialog

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/6/11 0011
 */
internal class LogDialog(context: Context) : BaseDialog(context, R.style.master_DialogTheme_Transparent) {
    private val recyclerView: RecyclerView
    var data = ArrayList<LogParser>()

    internal inner class TheViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.text)

    }

    fun setData(data: List<LogParser>?): LogDialog {
        this.data.clear()
        if (data == null) {
            return this
        }
        this.data.addAll(data)
        val adapter = recyclerView.adapter
        adapter?.notifyDataSetChanged()
        return this
    }

    fun notifyDataSetChanged() {
        recyclerView.adapter?.notifyDataSetChanged()
    }

    fun notifyItemInserted(position: Int) {
        recyclerView.adapter?.notifyItemInserted(position)
    }

    init {
        setContentView(R.layout.director_ooftf_dialog_show_log)
        setWidthPercent(1f)
        setHeightPercent(1f)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = object : RecyclerView.Adapter<TheViewHolder?>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheViewHolder {
                val theViewHolder = TheViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.director_ooftf_item_show_log, parent, false))
                theViewHolder.itemView.setOnLongClickListener { v ->
                    CopyUtil.copy(v.context, theViewHolder.textView.text.toString())
                    Toast.makeText(AppHolder.app, "已复制到粘贴板", Toast.LENGTH_SHORT).show()
                    true
                }
                return theViewHolder
            }

            override fun onBindViewHolder(holder: TheViewHolder, position: Int) {
                holder.textView.text = data[position].src
                when (data[position].level) {
                    Log.VERBOSE -> Color.GRAY
                    Log.DEBUG -> Color.GREEN
                    Log.INFO -> Color.BLUE
                    Log.WARN -> Color.YELLOW
                    Log.ERROR -> Color.RED
                    Log.ASSERT -> Color.WHITE
                    else -> Color.GRAY
                }.let {
                    holder.textView.setTextColor(it)
                }

            }

            override fun getItemCount(): Int {
                return data.size
            }
        }
    }
}