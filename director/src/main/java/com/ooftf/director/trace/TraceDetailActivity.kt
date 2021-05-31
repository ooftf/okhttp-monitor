package com.ooftf.director.trace

import android.os.Bundle
import android.renderscript.ScriptGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ooftf.basic.armor.ParamViewModelFactory
import com.ooftf.director.databinding.DirectorOoftfActivityTraceDetailBinding
import java.util.concurrent.Future
import java.util.concurrent.FutureTask


class TraceDetailActivity : AppCompatActivity() {
    val viewModel: TraceDetailViewModel by viewModels() {
        ParamViewModelFactory(application, intent.getStringExtra("path"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DirectorOoftfActivityTraceDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        
    }
}