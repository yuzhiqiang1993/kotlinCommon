package com.yzq.binding.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.yzq.logger.LogCat
import kotlin.properties.ReadOnlyProperty

/**
 * @description Fragment 绑定抽象类，主要处理生命周期相关逻辑
 * @author yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date 2022/11/8
 * @time 15:52
 */

abstract class FragmentBindingDelegate<B : ViewBinding>(
    fragment: Fragment
) : ReadOnlyProperty<Fragment, B> {

    protected var binding: B? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                val fragmentManager = fragment.parentFragmentManager
                fragmentManager.registerFragmentLifecycleCallbacks(
                    object :
                        FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
                            super.onFragmentViewDestroyed(fm, f)
                            if (f == fragment) {
                                LogCat.i("onFragmentViewDestroyed:$f")
                                binding = null
                                fragmentManager.unregisterFragmentLifecycleCallbacks(this)
                            }
                        }
                    },
                    false
                )
            }
        })
    }

//    init {
//        /**
//         *
//         * https://itnext.io/an-update-to-the-fragmentviewbindingdelegate-the-bug-weve-inherited-from-autoclearedvalue-7fc0a89fcae1
//         */
//        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
//            val viewLifecycleOwnerLiveDataObserver = Observer<LifecycleOwner?> {
//                val viewLifecycleOwner = it ?: return@Observer
//
//                viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
//                    override fun onDestroy(owner: LifecycleOwner) {
//                        binding = null
//                    }
//                })
//            }
//
//            override fun onCreate(owner: LifecycleOwner) {
//                /*fragment创建的时候添加监听*/
//                fragment.viewLifecycleOwnerLiveData.observeForever(
//                    viewLifecycleOwnerLiveDataObserver
//                )
//            }
//
//            override fun onDestroy(owner: LifecycleOwner) {
//                /*销毁的时候移除*/
//                fragment.viewLifecycleOwnerLiveData.removeObserver(
//                    viewLifecycleOwnerLiveDataObserver
//                )
//            }
//        })
//    }
}
