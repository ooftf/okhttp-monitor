package com.ooftf.http.monitor.holder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ooftf.director.app.Director
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Director.init("",true)
        Director.setDebugEntranceView(hw)
    }
}