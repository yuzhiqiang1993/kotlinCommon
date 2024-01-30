package com.yzq.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.DrawableRes
import androidx.annotation.GravityInt
import androidx.annotation.StyleRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.yzq.coroutine.safety_coroutine.runMain
import com.yzq.dialog.core.BaseDialogCallback
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

abstract class BaseDialogFragment : DialogFragment() {
    // 导入属性委托包
    var hostActivity: FragmentActivity? = null

    protected var dialogCancelable = false
    protected var dialogWidth: Int = ViewGroup.LayoutParams.WRAP_CONTENT
    protected var dialogHeight: Int = ViewGroup.LayoutParams.WRAP_CONTENT
    protected var dialogGravity: Int = Gravity.CENTER

    protected var dialogAnimStyle: Int = 0

    /*遮罩层的透明度*/
    protected var dialogDimAmount: Float = 0.5f

    /*dialog本身的透明度*/
    protected var dialogAlpha: Float = 1.0f

    protected var dialogBgRes: Int = 0

    val dialogTag: String = "BaseDialogFragment_${hashCode()}"

    private var callback: BaseDialogCallback? = null

    private var contentView: View? = null

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

    private fun removeContainerView() {/*避免出现同一个DialogFragment实例，二次显示时出现DialogFragment can not be attached to a container view的异常*/
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
        applyDialogConfig(dialog)
        return dialog
    }

    /**
     * 设置Dialog的配置
     */
    protected open fun applyDialogConfig(dialog: Dialog) {
        dialog.setCancelable(dialogCancelable)
        dialog.setCanceledOnTouchOutside(dialogCancelable)
        dialog.setOnKeyListener { dialog, keyCode, event ->
            if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
                Logger.i("onKeyBack:dialogCancelable:$dialogCancelable")
                !dialogCancelable
            } else {
                false
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
    protected open fun applyDialogWindow(window: Window) {
        if (dialogBgRes != 0) {
            window.setBackgroundDrawableResource(dialogBgRes)
        }
        window.attributes = window.attributes.apply {
            width = dialogWidth
            height = dialogHeight
            gravity = dialogGravity
            if (dialogAnimStyle != 0) {
                windowAnimations = dialogAnimStyle
            }
            dimAmount = dialogDimAmount
            alpha = dialogAlpha


        }
    }


    /**
     * 显示 DialogFragment，避免可能的异常
     */
    fun safeShow() {

        runCatching {
            hostActivity?.let {
                val fragmentManager = it.supportFragmentManager
                if (!it.isFinishing && !it.isDestroyed && !fragmentManager.isStateSaved) {
                    runMain {
                        show(fragmentManager, dialogTag)
                        callback?.onShow()
                    }

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
            hostActivity?.run {
                if (this.isDestroyed || this.isFinishing) return@runCatching
            }
            dialog?.run {
                if (this.isShowing) {
                    runMain {
                        dismissAllowingStateLoss()
                        callback?.onDissmiss()
                        //先移除，再添加，避免重复添加
                        contentView?.run {
                            (parent as ViewGroup).removeView(this)
                        }

                    }
                }
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    /**
     * 自动在指定的生命周期结束时调用 dismiss ,默认在宿主 onDestroy 时调用
     * @param dissmissEvent Array<out Lifecycle.Event>
     * @return BaseDialogFragment
     */

    @JvmOverloads
    fun autoDissmiss(vararg dissmissEvent: Lifecycle.Event = arrayOf(Lifecycle.Event.ON_DESTROY)): BaseDialogFragment {
        this.hostActivity?.lifecycle?.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event in dissmissEvent) {
                    safeDismiss()
                }
            }
        })
        return this
    }


    override fun onStop() {
        super.onStop()
        Logger.i("onStop")
    }


    open fun width(width: Int): BaseDialogFragment {
        this.dialogWidth = width
        return this
    }

    fun height(height: Int): BaseDialogFragment {
        this.dialogHeight = height
        return this
    }

    /**
     * 设置位置
     * @param gravity Int
     * @return BaseDialogFragment
     */
    fun gravity(@GravityInt gravity: Int): BaseDialogFragment {
        this.dialogGravity = gravity
        return this
    }

    /**
     * 设置动画
     * @param animStyle Int
     * @return BaseDialogFragment
     */
    fun animStyle(@StyleRes animStyle: Int): BaseDialogFragment {
        this.dialogAnimStyle = animStyle
        return this
    }

    /**
     * 遮罩层的透明度，0.0f-1.0f，0.0f表示完全透明，1.0f表示完全不透明
     * @param dimAmount Float
     * @return BaseDialogFragment
     */
    fun dimAmount(dimAmount: Float): BaseDialogFragment {
        this.dialogDimAmount = dimAmount
        return this
    }

    fun cancelable(cancelable: Boolean): BaseDialogFragment {
        this.dialogCancelable = cancelable
        return this
    }

    fun alpha(alpha: Float): BaseDialogFragment {
        this.dialogAlpha = alpha
        return this
    }

    fun bgRes(@DrawableRes resId: Int): BaseDialogFragment {
        this.dialogBgRes = resId
        return this
    }


    fun callback(callback: BaseDialogCallback): BaseDialogFragment {
        this.callback = callback
        return this
    }

    override fun onDetach() {
        super.onDetach()
        Logger.i("onDetach")
        removeContainerView()
    }

}

