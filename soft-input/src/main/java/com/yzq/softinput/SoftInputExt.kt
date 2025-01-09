package com.yzq.softinput

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.ViewTreeObserver
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.yzq.application.AppContext.getSystemService


private const val TAG = "SoftInputExt"


/**
 * 弹出软键盘
 * @receiver EditText
 */
fun EditText.showSoftInput() {
    isFocusable = true
    isFocusableInTouchMode = true
    requestFocus()
    postDelayed({
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, 0)
    }, 300)
}


/**
 * 隐藏软键盘
 * @receiver Activity
 */
fun Activity.hideSoftInput() {
    window.hideSoftInput()
}

/**
 * 隐藏键盘
 */
fun Window.hideSoftInput() {
    kotlin.runCatching {
        currentFocus?.let {
            val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        } ?: let {
            WindowCompat.getInsetsController(this, decorView)
                .hide(WindowInsetsCompat.Type.ime())
        }
    }
}

/**
 * 隐藏软键盘
 * @receiver Fragment
 */
fun Fragment.hideSoftInput() = requireActivity().hideSoftInput()


/**
 * 软键盘是否显示
 * @receiver Activity
 * @return Boolean
 */
fun Activity.softInputIsShowing(): Boolean {
    return ViewCompat.getRootWindowInsets(window.decorView)
        ?.isVisible(WindowInsetsCompat.Type.ime()) ?: false
}

fun Fragment.softInputIsShowing(): Boolean {
    return requireActivity().softInputIsShowing()
}

/**
 * 获取软键盘高度
 * @receiver Activity
 * @return Int
 */
fun Activity.softInputHeight(): Int {
    val softInputHeight = ViewCompat.getRootWindowInsets(window.decorView)
        ?.getInsets(WindowInsetsCompat.Type.ime())?.bottom
    return softInputHeight ?: 0
}

fun Fragment.softInputHeight(): Int {
    return requireActivity().softInputHeight()
}

@JvmOverloads
fun Activity.setWindowSoftInput(
    floatView: View, transitionView: View? = null, editText: View? = null
) {
    window.setWindowSoftInput(floatView, transitionView, editText)
}

@JvmOverloads
fun Fragment.setWindowSoftInput(
    floatView: View,
    transitionView: View? = null,
    editText: View? = null,
) {
    requireActivity().window.setWindowSoftInput(
        floatView, transitionView, editText
    )
}

@JvmOverloads
fun DialogFragment.setWindowSoftInput(
    floatView: View,
    transitionView: View? = null,
    editText: View? = null,
) {
    dialog?.window?.setWindowSoftInput(floatView, transitionView, editText)
}

@JvmOverloads
fun Dialog.setWindowSoftInput(
    floatView: View,
    transitionView: View? = window?.decorView,
    editText: View? = null,
) = window?.setWindowSoftInput(floatView, transitionView, editText)


/**
 * 软键盘弹出后让指定的[floatView]悬浮在软键盘之上
 * @receiver Window 当前Window
 * @param floatView View? 需要悬浮在软键盘之上的视图
 * @param transitionView View? 当软键盘显示隐藏时需要移动的视图, 使用[View.setTranslationY]移动
 * @param editText View?    指定的视图存在焦点才触发软键盘监听, null则全部视图都触发
 * @param margin Int    悬浮视图和软键盘间距
 */
private fun Window.setWindowSoftInput(
    floatView: View,
    transitionView: View? = null,
    editText: View? = null,
    lifecycle: Lifecycle? = null
) {
    //设置软键盘弹出模式为 SOFT_INPUT_ADJUST_NOTHING: 不调整窗口，自行处理
    if (this.attributes.softInputMode != WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING) {
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }

    // 是否需要浮动
    var needFloat = false
    //浮动视图的底部坐标
    var floatViewBottom = 0
    //动画时长
    val animateDuration = 150L

    val globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        //先获取要浮动在软键盘上方的View的底部坐标
        if (floatViewBottom == 0) {
            /*
            * 获取浮动视图的底部坐标,只有第一次获取，因为如果存在键盘类型切换，比如数字键盘，和密码键盘，这两个键盘的高度不一样，如果每次都获取，切换键盘时会导致计算不准确
            * 在已经显示键盘的情况下,切换键盘类型,再次计算应该以第一次获取的为准，而不是重新获取
            * */
            val viewLocation = IntArray(2)
            floatView.getLocationInWindow(viewLocation)
            floatViewBottom = viewLocation[1] + floatView.height
        }

        //获取DecorView的底部坐标
        val decorBottom = decorView.bottom

        //获取软键盘高度
        val rootWindowInsets =
            ViewCompat.getRootWindowInsets(decorView) ?: return@OnGlobalLayoutListener

        val softInputHeight = rootWindowInsets.getInsets(WindowInsetsCompat.Type.ime()).bottom

        //软键盘是否显示
        val hasSoftInput = rootWindowInsets.isVisible(WindowInsetsCompat.Type.ime())
        //计算偏移量，根视图的底部坐标减去浮动视图的底部坐标减去软键盘高度减去间距
        val offset = (decorBottom - floatViewBottom - softInputHeight).toFloat()
        if (hasSoftInput) {
            //键盘弹出了，需要浮动
            needFloat = editText == null || editText.hasFocus()
            if (needFloat) {
                if (transitionView != null) {
                    transitionView.animate().translationY(offset).setDuration(animateDuration)
                        .start()
                } else {
                    floatView.animate().translationY(offset).setDuration(animateDuration).start()
                }
            }
        } else {
            //键盘隐藏了，如果需要浮动，就恢复原位
            if (needFloat) {
                if (transitionView != null) {
                    transitionView.animate().translationY(0f).setDuration(animateDuration).start()
                } else {
                    floatView.animate().translationY(0f).setDuration(animateDuration).start()
                }
            }
        }

    }


    if (lifecycle == null) {
        decorView.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
    } else {
        lifecycle.addObserver(object : androidx.lifecycle.DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                decorView.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
            }

            override fun onPause(owner: LifecycleOwner) {
                decorView.viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
                this@setWindowSoftInput.hideSoftInput()
            }
        })
    }
}


