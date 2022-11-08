package com.yzq.binding.databinding

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.yzq.binding.base.FragmentBindingDelegate
import kotlin.reflect.KProperty


/**
 * @description Fragment DataBinding代理
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2022/11/8
 * @time    13:31
 */

class FragmentDataBindingDelegate<VDB : ViewDataBinding>(
    fragment: Fragment,
) : FragmentBindingDelegate<VDB>(fragment) {


    override fun getValue(thisRef: Fragment, property: KProperty<*>): VDB {
        binding?.let { return it }
        val dataBinding = DataBindingUtil.bind<VDB>(thisRef.requireView())
        binding = dataBinding
        return binding as VDB
    }


}