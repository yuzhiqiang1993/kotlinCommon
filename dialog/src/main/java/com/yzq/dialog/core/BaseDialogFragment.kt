package com.yzq.dialog.core

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.yzq.coroutine.ext.runMain
import com.yzq.logger.Logger


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
 *
 */


abstract class BaseDialogFragment<T : BaseDialogFragment<T>>(
    private val hostActivity: FragmentActivity
) : DialogFragment() {

    val dialogTag: String = "BaseDialogFragment_${hashCode()}"

    private var callback: BaseDialogCallback? = null

    private var contentView: View? = null

    /**
     * Dialog的配置类
     */
    protected val config: DialogConfig by lazy {
        DialogConfig()
    }

    init {
        this.hostActivity.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_DESTROY -> safeDismiss()
                    else -> {}
                }
            }
        })
    }


    /**
     * 设置Dialog的配置，方便kotlin用
     * @param block DialogConfig.() -> Unit
     */
    @Suppress("UNCHECKED_CAST")
    open fun config(block: DialogConfig.() -> Unit): T {
        config.block()
        return this as T
    }

    /**
     * 设置Dialog的配置，方便给Java代码用
     * @param config DialogConfig
     */
    @Suppress("UNCHECKED_CAST")
    open fun setDialogConfig(config: DialogConfig): T {
        this.config.apply {
            dialogCancelable = config.dialogCancelable
            dialogWidth = config.dialogWidth
            dialogHeight = config.dialogHeight
            dialogGravity = config.dialogGravity
            dialogAnimStyle = config.dialogAnimStyle
            dialogDimAmount = config.dialogDimAmount
            dialogAlpha = config.dialogAlpha
            dialogBgRes = config.dialogBgRes
        }

        return this as T
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.i("onCreate")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        Logger.i("onCreateView:contentView:$contentView")
        if (contentView == null) {
            contentView = initView(inflater, container, savedInstanceState)
        }
        removeContainerView()
        return contentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentView?.run {
            initWidget(this)
        }
    }

    abstract fun initWidget(contentView: View)


    private fun removeContainerView() {
        /**
         * 这里先移除一下
         * 避免出现同一个DialogFragment实例，二次显示时出现DialogFragment can not be attached to a container view的异常
         */
        runMain {
            contentView?.let {
                it.parent?.run {
                    if (this is ViewGroup) {
                        this.removeView(it)
                    }
                }
            }
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Logger.i("onAttach")
    }

    abstract fun initView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialogConfig(config)
        applyDialogConfig(dialog)
        return dialog
    }

    /**
     * 给子类提供的设置Dialog的配置的方法
     * @param config DialogConfig
     * @return BaseDialogFragment
     */
    protected open fun dialogConfig(config: DialogConfig) {}


    /**
     * 应用Dialog的配置
     */
    private fun applyDialogConfig(dialog: Dialog) {
        dialog.apply {
            setCancelable(config.dialogCancelable)
            setCanceledOnTouchOutside(config.dialogCancelable)
            setOnKeyListener { _, keyCode, _ ->
                when (keyCode) {
                    KeyEvent.KEYCODE_BACK -> !config.dialogCancelable
                    else -> false
                }
            }
        }
    }


    override fun onStart() {
        super.onStart()
        Logger.i("onStart")
        dialog?.window?.let {
            applyDialogWindow(it)
        }

    }

    override fun onResume() {
        super.onResume()
        Logger.i("onResume")
    }


    override fun onPause() {
        super.onPause()
        Logger.i("onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.i("onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Logger.i("onDestroyView")
    }

    /**
     * 设置Dialog的window属性
     * @param window Window
     */
    private fun applyDialogWindow(window: Window) {
        if (config.dialogBgRes != 0) {
            window.setBackgroundDrawableResource(config.dialogBgRes)
        }
        window.attributes = window.attributes.apply {
            width = config.dialogWidth
            height = config.dialogHeight
            gravity = config.dialogGravity
            if (config.dialogAnimStyle != 0) {
                windowAnimations = config.dialogAnimStyle
            }
            dimAmount = config.dialogDimAmount
            alpha = config.dialogAlpha
        }
    }


    /**
     * 显示 DialogFragment，避免可能的异常
     */
    fun safeShow() {
        runCatching {
            val fragmentManager = hostActivity.supportFragmentManager
            if (!hostActivity.isFinishing && !hostActivity.isDestroyed && !fragmentManager.isStateSaved) {
                runMain {
                    show(fragmentManager, dialogTag)
                    callback?.onShow()
                }
            }
        }.onFailure {
            it.printStackTrace()
        }
    }


    /**
     * 安全地调用 dismiss，避免可能的异常
     */
    open fun safeDismiss() {
        runCatching {
            if (isShowing()) {
                runMain {
                    dismissAllowingStateLoss()
                }
            }
        }.onFailure {
            it.printStackTrace()
        }
    }


    protected fun isShowing(): Boolean {
        if (hostActivity.isDestroyed || hostActivity.isFinishing) return false
        return dialog?.isShowing ?: false
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        callback?.onDissmiss()
    }

    override fun onStop() {
        super.onStop()
        Logger.i("onStop")
    }


    override fun onDetach() {
        super.onDetach()
        Logger.i("onDetach")
        removeContainerView()
    }


    @Suppress("UNCHECKED_CAST")
    fun setCallback(callback: BaseDialogCallback): T {
        this.callback = callback
        return this as T
    }
}

