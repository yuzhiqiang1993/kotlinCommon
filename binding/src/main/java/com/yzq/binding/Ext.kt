package com.yzq.binding

import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.app.ComponentActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


/**
 * Activity viewbinding
 *
 * @param VB
 * @param inflate
 * @receiver
 */
fun <VB : ViewBinding> ComponentActivity.viewbind(
    inflate: (LayoutInflater) -> VB
) = ActivityViewBindingDelegate(inflate)


/**
 * Databind
 *
 * @param DB
 * @param contentLayoutId
 */
fun <DB : ViewDataBinding> ComponentActivity.databind(@LayoutRes contentLayoutId: Int) =
    ActivityDataBindingDelegate<DB>(contentLayoutId)


fun <VB : ViewBinding> Fragment.viewbind(bind: (View) -> VB) =
    FragmentViewBindingDelegate(this, bind)


fun <VDB : ViewDataBinding> Fragment.databind(@LayoutRes contentLayoutId: Int) =
    FragmentDataBindingDelegate<VDB>(this, contentLayoutId)