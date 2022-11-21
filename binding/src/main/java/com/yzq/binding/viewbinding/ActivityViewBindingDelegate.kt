package com.yzq.binding.viewbinding

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.core.app.ComponentActivity
import androidx.viewbinding.ViewBinding
import com.yzq.binding.base.ActivityBindingDelegate
import kotlin.reflect.KProperty

/**
 * @description Activity 中 ViewBingding 的委托实现
 * @author yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date 2022/10/21
 * @time 15:20
 */

@SuppressLint("RestrictedApi")
class ActivityViewBindingDelegate<VB : ViewBinding>(
    activity: ComponentActivity,
    private val inflate: (LayoutInflater) -> VB
) : ActivityBindingDelegate<VB>(activity) {

    override operator fun getValue(thisRef: ComponentActivity, property: KProperty<*>): VB {
        binding?.let { return it }
        val viewBinding = inflate(thisRef.layoutInflater)
        thisRef.setContentView(viewBinding.root)
        binding = viewBinding
        return binding as VB
    }

}
