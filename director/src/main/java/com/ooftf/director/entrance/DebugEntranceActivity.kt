package com.ooftf.director.entrance

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ooftf.director.R
import com.ooftf.director.databinding.DirectorOoftfActivityCommonListBinding


class DebugEntranceActivity : AppCompatActivity() {
    val viewModel: DebugEntranceViewModel by viewModels()
    lateinit var binding: DirectorOoftfActivityCommonListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.director_ooftf_activity_common_list)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }
}