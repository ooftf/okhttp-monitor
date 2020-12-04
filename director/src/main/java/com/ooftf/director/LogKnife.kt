package com.ooftf.director

import android.os.Handler
import android.os.Looper
import android.os.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.collections.ArrayList

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/9/1
 */
object LogKnife {
    var queue = Vector<LogParser>()
    const val cacheSize = 200
    const val MESSAGE_PUBLISH_LOG = 4896
    private var isRunning = true
    private val internalHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            if (msg?.what == MESSAGE_PUBLISH_LOG) {
                while (queue.size >= cacheSize) {
                    //queue.poll()
                    queue.removeAt(queue.size - 1)
                }
                //queue.offer(msg.obj.toString())
                LogParser(msg.obj.toString()).let { letIt ->
                    queue.add(0, letIt)
                    dataChangeListener.forEach { it(letIt) }
                }
            }
        }
    }

    init {
        GlobalScope.launch(Dispatchers.IO) {
            obtainLog()
        }
    }

    var dataChangeListener = ArrayList<(LogParser) -> Unit>();
    fun registerDataChange(listener: (LogParser) -> Unit) {
        dataChangeListener.add(listener)
    }

    fun unregisterDataChange(listener: (LogParser) -> Unit) {
        dataChangeListener.remove(listener)
    }

    fun obtainLog() {
        Runtime.getRuntime().exec("logcat -c")
        val process = Runtime.getRuntime().exec("logcat -v time")
        val iStream = process.inputStream
        val reader = InputStreamReader(iStream);
        val br = BufferedReader(reader);

        var log: String?
        while (br.readLine().also { log = it } != null && isRunning) {
            if (log == null) {
                continue
            }
            val message = Message.obtain();
            message.what = MESSAGE_PUBLISH_LOG;
            message.obj = log;
            internalHandler.sendMessage(message)
        }
        br.close()
        reader.close()
        iStream.close()
    }


}