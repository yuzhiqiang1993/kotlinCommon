package com.yzq.binding.base

import android.annotation.SuppressLint
import androidx.core.app.ComponentActivity
import androidx.lifecycle.*
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty


/**
 * @description 处理生命周期相关逻辑的代理
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2022/11/8
 * @time    15:48
 */

@SuppressLint("RestrictedApi")
abstract class ActivityBindingDelegate<B : ViewBinding>(activity: ComponentActivity) :
    ReadOnlyProperty<ComponentActivity, B> {

    protected var binding: B? = null

    init {
        activity.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)
                binding = null
            }
        })

    }

}
