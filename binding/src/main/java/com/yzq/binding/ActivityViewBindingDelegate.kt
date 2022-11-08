package com.yzq.binding

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.core.app.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


/**
 * @description Activity 中 ViewBingding 的委托实现
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2022/10/21
 * @time    15:20
 */

class ActivityViewBindingDelegate<in A : ComponentActivity, VB : ViewBinding>(
    private val inflate: (layoutInflater: LayoutInflater) -> VB
) : ReadOnlyProperty<A, VB> {

    private var _viewBinding: VB? = null


    @SuppressLint("RestrictedApi")
    override operator fun getValue(thisRef: A, property: KProperty<*>): VB {
        _viewBinding?.let { return it }
        /**
         * 本质上就是在Activity中把他的 inflate(layoutInflater) 方法的引用传过来，在这里调用一下
         */
        val viewBinding = inflate(thisRef.layoutInflater)

        thisRef.setContentView(viewBinding.root)
        this._viewBinding = viewBinding

        /**
         * onDestory的时候销毁 Activity其实不用这个步骤
         */
        thisRef.lifecycle.addObserver(BindingLifecycleObserver())

        return viewBinding
    }


    private inner class BindingLifecycleObserver : DefaultLifecycleObserver {

        override fun onDestroy(owner: LifecycleOwner) {
            _viewBinding = null
        }
    }
}
