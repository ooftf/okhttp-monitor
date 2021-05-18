package com.ooftf.director

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ooftf.basic.armor.ObservableArrayListPro
import me.tatarka.bindingcollectionadapter2.ItemBinding
/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/5/21
 */
abstract class CommonListViewModel(application: Application) :
    AndroidViewModel(application) {
    val items = ObservableArrayListPro<Item>()
    val itemBinding  = ItemBinding.of<Item>(BR.item,R.layout.director_ooftf_item)
    val title = MutableLiveData<String>()
}