package com.ooftf.director

import androidx.lifecycle.MutableLiveData
import com.ooftf.basic.armor.InitLiveData


class Item(key: String = "", value: String = "", var listener: (() -> Unit)? = null) {
    var key = InitLiveData(key)
    var isChecked = MutableLiveData<Boolean>()
    var value = InitLiveData<String>(value)

}