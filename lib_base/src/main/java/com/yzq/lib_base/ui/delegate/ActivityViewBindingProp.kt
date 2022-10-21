package com.yzq.lib_base.ui.delegate

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
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
class ActivityViewBindingProp<in A : AppCompatActivity, B : ViewBinding>(
    private val inflate: (layoutInflater: LayoutInflater) -> B
) : ReadOnlyProperty<A, B> {

    private var _viewBinding: B? = null

    operator override fun getValue(thisRef: A, property: KProperty<*>): B {
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
        return this._viewBinding!!
    }
}
