package com.yzq.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity


/**
 * @description: 基础的DialogFragment
 * @author : yuzhiqiang
 * 关键生命周期顺序
 * onAttach()： 当 Fragment 被附加到 Activity 时调用。
 * onCreate()： 在 Fragment 被创建时调用。
 * onCreateDialog()： 创建对话框，用于设置和初始化 Dialog。
 * onCreateView()： 在 Fragment 的视图被创建时调用。
 * onViewCreated()： 在 onCreateView 执行完成后调用，用于初始化视图的工作。
 * onStart()： 当 Fragment 可见时调用。
 * onResume()： 当 Fragment 获得焦点并可交互时调用。
 * onPause()： 当 Fragment 失去焦点时调用。
 * onStop()： 当 Fragment 不可见时调用。
 * onDestroyView()： 在 Fragment 的视图被销毁时调用。
 * onDestroy()： 在 Fragment 被销毁时调用。
 * onDetach()： 当 Fragment 与 Activity 分离时调用。
 * 在 DialogFragment 中，
 * 创建和显示对话框的生命周期顺序通常是：onAttach -> onCreate -> onCreateDialog -> onCreateView -> onViewCreated -> onStart -> onResume。
 * 而关闭对话框时的生命周期顺序通常是：onPause -> onStop -> onDestroyView -> onDestroy -> onDetach。
 */

abstract class BaseDialogFragment : DialogFragment() {

    var hostActivity: FragmentActivity? = null


    val dialogTag: String = "BaseDialogFragment_${hashCode()}"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return initView(inflater, container, savedInstanceState)
    }


    abstract fun initView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        applyDialogConfig(dialog)
        return dialog
    }

    /**
     * 设置Dialog的配置
     */
    abstract fun applyDialogConfig(dialog: Dialog)


    override fun onStart() {
        super.onStart()
        dialog?.window?.let {
            applyDialogWindow(it)
        }


    }


    /**
     * 设置Dialog的window属性
     * @param window Window
     */
    abstract fun applyDialogWindow(window: Window)


    /**
     * 显示 DialogFragment，避免可能的异常
     */
    fun safeShow() {
        runCatching {
            hostActivity?.let {
                val fragmentManager = it.supportFragmentManager
                if (!fragmentManager.isStateSaved) {
                    super.show(fragmentManager, dialogTag)
                }
            }
        }.onFailure {
            it.printStackTrace()
        }
    }


    /**
     * 安全地调用 dismiss，避免可能的异常
     */
    fun safeDismiss() {
        runCatching {
            if (isAdded && hostActivity?.isFinishing == false && dialog?.isShowing == true) {
                super.dismiss()
            }
        }.onFailure {
            it.printStackTrace()
        }

    }


}
