package com.yzq.binding

import android.annotation.SuppressLint
import androidx.annotation.LayoutRes
import androidx.core.app.ComponentActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


/**
 * @description Activity 中 DataBinding 的委托实现
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2022/10/21
 * @time    15:20
 */

class ActivityDataBindingDelegate<in A : ComponentActivity, VDB : ViewDataBinding>(@LayoutRes val contentLayoutId: Int) :
    ReadOnlyProperty<A, VDB> {

    private var _dataBinding: VDB? = null


    @SuppressLint("RestrictedApi")
    override operator fun getValue(thisRef: A, property: KProperty<*>): VDB {
        _dataBinding?.let { return it }

        val dataBinding = DataBindingUtil.setContentView<VDB>(thisRef, contentLayoutId)

        this._dataBinding = dataBinding

        thisRef.lifecycle.addObserver(BindingLifecycleObserver())

        return dataBinding
    }


    private inner class BindingLifecycleObserver : DefaultLifecycleObserver {

        override fun onDestroy(owner: LifecycleOwner) {
            _dataBinding = null
        }
    }
}
