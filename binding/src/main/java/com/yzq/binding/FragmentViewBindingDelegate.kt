package com.yzq.binding

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


/**
 * @description Fragment 的 viewbinding 代理
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2022/11/7
 * @time    17:56
 */

class FragmentViewBindingDelegate<VB : ViewBinding>(
    fragment: Fragment,
    val bind: (View) -> VB
) :
    ReadOnlyProperty<Fragment, VB> {


    private var _viewBinding: VB? = null


    init {
        /**
         * https://itnext.io/an-update-to-the-fragmentviewbindingdelegate-the-bug-weve-inherited-from-autoclearedvalue-7fc0a89fcae1
         */
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            val viewLifecycleOwnerLiveDataObserver =
                Observer<LifecycleOwner?> {
                    val viewLifecycleOwner = it ?: return@Observer

                    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            _viewBinding = null
                        }
                    })
                }

            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observeForever(
                    viewLifecycleOwnerLiveDataObserver
                )
            }

            override fun onDestroy(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.removeObserver(
                    viewLifecycleOwnerLiveDataObserver
                )
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        if (_viewBinding != null) {
            return _viewBinding!!
        }
        val binding = bind.invoke(thisRef.requireView())
        _viewBinding = binding
        return binding
    }


}