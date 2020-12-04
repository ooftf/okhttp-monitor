package com.ooftf.http.monitor.holder

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ooftf.http.monitor.serializable.AllUrls

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AllUrls.get().add("456798")
        AllUrls.sync()
        Log.e("DiskUrls", AllUrls.get().javaClass.name)
    }
}