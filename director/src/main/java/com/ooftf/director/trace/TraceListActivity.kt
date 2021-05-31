package com.ooftf.director.trace

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ooftf.director.Item
import com.ooftf.director.R
import com.ooftf.director.databinding.DirectorOoftfActivityCommonListBinding
import com.ooftf.director.info.DebugInfoViewModel

class TraceListActivity: AppCompatActivity() {
    val viewModel : TraceListViewModel by viewModels()
    lateinit var binding: DirectorOoftfActivityCommonListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.director_ooftf_activity_common_list)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }
}