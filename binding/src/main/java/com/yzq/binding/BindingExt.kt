package com.yzq.binding

import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.app.ComponentActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.yzq.binding.databinding.ActivityDataBindingDelegate
import com.yzq.binding.databinding.FragmentDataBindingDelegate
import com.yzq.binding.viewbinding.ActivityViewBindingDelegate
import com.yzq.binding.viewbinding.FragmentViewBindingDelegate

inline fun <reified VB : ViewBinding> ComponentActivity.viewbind(
    noinline inflate: (LayoutInflater) -> VB,
) = ActivityViewBindingDelegate(this, inflate)

inline fun <reified DB : ViewDataBinding> ComponentActivity.databind(@LayoutRes contentLayoutId: Int) =
    ActivityDataBindingDelegate<DB>(this, contentLayoutId)

inline fun <reified VB : ViewBinding> Fragment.viewbind(noinline bind: (View) -> VB) =
    FragmentViewBindingDelegate(this, bind)

inline fun <reified VDB : ViewDataBinding> Fragment.databind() =
    FragmentDataBindingDelegate<VDB>(this)
