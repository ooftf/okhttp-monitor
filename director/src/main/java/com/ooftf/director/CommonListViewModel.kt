package com.ooftf.director

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ooftf.arch.frame.mvvm.vm.BaseListViewModel

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/5/21
 */
abstract class CommonListViewModel<T>(application: Application) :
    BaseListViewModel<T>(application) {
    val title = MutableLiveData<String>()
}