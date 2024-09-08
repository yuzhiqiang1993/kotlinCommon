import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewTreeObserver
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

/**
 * 让指定View随着软键盘的弹出和隐藏而浮动 (贼麻烦，各种屏幕尺寸的兼容情况未知，要多测～)
 * @receiver Activity 当前Activity
 * @param floatView View? 要浮动的View
 * @param editText View? 响应的EditText, 如果不为null，表示只有该EditText获取焦点时才会浮动
 */
@JvmOverloads
internal fun Activity.floatWithSoftInput(
    floatView: View, editText: EditText? = null
) {
    val lifecycle = if (this is ComponentActivity) {
        this.lifecycle
    } else {
        null
    }

    window.floatWithSoftInput(floatView, editText, lifecycle)
}


/**
 * 让指定View随着软键盘的弹出和隐藏而浮动
 * @receiver Window 当前Window
 * @param floatView View 要浮动的View
 * @param editText View? 响应的EditText 如果不为null，表示只有该EditText获取焦点时才会浮动
 */
@JvmOverloads
internal fun Window.floatWithSoftInput(
    floatView: View, editText: EditText? = null, lifecycle: Lifecycle? = null
) {

    //设置软键盘弹出模式为 SOFT_INPUT_ADJUST_NOTHING: 不调整窗口，其他的会导致计算键盘高度不准确
    if (this.attributes.softInputMode != WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING) {
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }

    var floatViewBottom = 0
    var isShowing = false
    var needFloat = false
    val animateDuration = 200L
    val delayMillis = 10L
    val handler = Handler(Looper.getMainLooper())
    var pendingTask: Runnable? = null

    val globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        kotlin.runCatching {

            val rootWindowInsets = ViewCompat.getRootWindowInsets(decorView)
            val hasSoftInput = rootWindowInsets?.isVisible(WindowInsetsCompat.Type.ime()) ?: false
            val softInputHeight =
                rootWindowInsets?.getInsets(WindowInsetsCompat.Type.ime())?.bottom ?: 0

            pendingTask?.let {
                handler.removeCallbacks(it)
            }

            //避免频繁调用
            pendingTask = Runnable {
                if (floatViewBottom == 0) {
                    val floatViewLocation = IntArray(2)
                    floatView.getLocationInWindow(floatViewLocation)
                    floatViewBottom = floatViewLocation[1] + floatView.height
                }
                val decorViewBottom = decorView.bottom
                val offset = (decorViewBottom - floatViewBottom - softInputHeight).toFloat()
                if (hasSoftInput) {
                    needFloat = editText == null || editText.hasFocus()
                    if (needFloat) {
                        floatView.animate().translationY(offset).setDuration(animateDuration)
                            .start()
                    }
                    isShowing = true
                } else {
                    if (isShowing && needFloat) {
                        floatView.animate().translationY(0f).setDuration(animateDuration).start()
                    }
                    isShowing = false
                }
            }

            handler.postDelayed(pendingTask!!, delayMillis)
        }.onFailure {
            it.printStackTrace()
        }
    }


    // 监听器跟生命周期绑定，避免内存泄漏
    lifecycle?.addObserver(object : DefaultLifecycleObserver {
        override fun onResume(owner: LifecycleOwner) {
            decorView.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
        }

        override fun onPause(owner: LifecycleOwner) {
            decorView.viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
        }

        override fun onDestroy(owner: LifecycleOwner) {
            decorView.viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
            handler.removeCallbacksAndMessages(null)
        }
    })
}