package com.yzq.base.ui.delegate

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.core.app.ComponentActivity
import androidx.lifecycle.*
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.LogUtils
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


/**
 * @description Activity 中 ViewBingding 的委托实现
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2022/10/21
 * @time    15:20
 */

@SuppressLint("RestrictedApi")
class ActivityViewBindingProp<in A : ComponentActivity, B : ViewBinding>(
    private val inflate: (layoutInflater: LayoutInflater) -> B
) : ReadOnlyProperty<A, B> {

    private var _viewBinding: B? = null


    override operator fun getValue(thisRef: A, property: KProperty<*>): B {
        _viewBinding?.let { return it }
        LogUtils.i("thisRef:$thisRef")

        LogUtils.i("viewBinder:${inflate}")

        /**
         * 本质上就是在Activity中把他的 inflate(layoutInflater) 方法的引用传过来，在这里调用一下
         */
        val viewBinding = inflate(thisRef.layoutInflater)
        LogUtils.i("viewBinding:${viewBinding}")

        thisRef.setContentView(viewBinding.root)
        LogUtils.i("setContentView")
        this._viewBinding = viewBinding


        /**
         * onDestory的时候销毁
         */
        thisRef.lifecycle.addObserver(BindingLifecycleOvserver())

        return this._viewBinding!!
    }


    private inner class BindingLifecycleOvserver : DefaultLifecycleObserver {

        override fun onDestroy(owner: LifecycleOwner) {
            _viewBinding = null

        }
    }
}
