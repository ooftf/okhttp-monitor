package com.ooftf.director.trace

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ooftf.basic.armor.ObservableArrayListPro
import com.ooftf.director.BR
import com.ooftf.director.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.tatarka.bindingcollectionadapter2.ItemBinding
import java.io.File

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2021/5/31
 */
class TraceDetailViewModel(application: Application, val path: String) :
    AndroidViewModel(application) {
    val items = ObservableArrayListPro<String>()
    val itemBinding = ItemBinding.of<String>(BR.item, R.layout.director_ooftf_item_text)

    init {
        GlobalScope.launch(Dispatchers.IO) {
            val data = File(path).readLines()
            withContext(Dispatchers.Main) {
                items.addAll(data)
            }
        }
    }
}