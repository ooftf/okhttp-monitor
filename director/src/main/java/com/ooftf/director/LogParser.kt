package com.ooftf.director

import android.util.Log

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/9/2
 * 2020-09-02 17:32:50.352 31754-31800/com.master.kit D/LeakCanary: Check for retained object found no objects remaining
 */
class LogParser(var src: String) {
    var date = ""
    var time = ""
    var level = Log.VERBOSE
    var tag = ""
    var content = ""

    init {
        val split = src.split(" ")
        date = split[0]
        time = split[1]
        when (split[2].first()) {
            'V' -> level = Log.VERBOSE
            'D' -> level = Log.DEBUG
            'I' -> level = Log.INFO
            'W' -> level = Log.WARN
            'E' -> level = Log.ERROR
            'A' -> level = Log.ASSERT
            else -> Log.VERBOSE
        }
        tag = split[2].substring(2)
        content = split[3]
    }
}