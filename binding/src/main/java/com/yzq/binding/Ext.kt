package com.yzq.binding

import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.core.app.ComponentActivity
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding


/**
 * Activity viewbinding
 *
 * @param VB
 * @param inflate
 * @receiver
 */
fun <VB : ViewBinding> ComponentActivity.viewbind(
    inflate: (layoutInflater: LayoutInflater) -> VB
) = ActivityViewBindingDelegate<ComponentActivity, VB>(inflate)


/**
 * Databind
 *
 * @param DB
 * @param contentLayoutId
 */
fun <DB : ViewDataBinding> ComponentActivity.databind(@LayoutRes contentLayoutId: Int) =
    ActivityDataBindingDelegate<ComponentActivity, DB>(contentLayoutId)