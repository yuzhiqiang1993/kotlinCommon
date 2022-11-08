package com.yzq.binding.databinding

import android.annotation.SuppressLint
import androidx.annotation.LayoutRes
import androidx.core.app.ComponentActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.yzq.binding.base.ActivityBindingDelegate
import kotlin.reflect.KProperty


/**
 * @description Activity 中 DataBinding 的委托实现
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2022/10/21
 * @time    15:20
 */

@SuppressLint("RestrictedApi")
class ActivityDataBindingDelegate<VDB : ViewDataBinding>(
    activity: ComponentActivity,
    @LayoutRes val contentLayoutId: Int
) : ActivityBindingDelegate<VDB>(activity) {

    private var _dataBinding: VDB? = null


    init {
        activity.lifecycle.addObserver(BindingLifecycleObserver())

    }

    @SuppressLint("RestrictedApi")
    override operator fun getValue(thisRef: ComponentActivity, property: KProperty<*>): VDB {
        _dataBinding?.let { return it }

        val dataBinding = DataBindingUtil.setContentView<VDB>(thisRef, contentLayoutId)

        _dataBinding = dataBinding
        _dataBinding?.lifecycleOwner = thisRef

        return _dataBinding as VDB
    }


    private inner class BindingLifecycleObserver : DefaultLifecycleObserver {

        override fun onDestroy(owner: LifecycleOwner) {
            _dataBinding = null
        }
    }
}
