package com.yzq.util.ext

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import android.view.View
import android.view.View.NO_ID
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RelativeLayout
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity

private const val COLOR_TRANSPARENT = 0


/**
 * Status bar color
 * 设置状态栏颜色
 * @param color
 */
fun Activity.statusBarColor(@ColorInt color: Int) {
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window?.statusBarColor = color
}


/**
 * Status bar color res
 * 设置状态栏颜色
 * @param colorRes
 */
fun Activity.statusBarColorRes(@ColorRes colorRes: Int) =
    statusBarColor(resources.getColor(colorRes))


// <editor-fold desc="透明状态栏">
/**
 * 使用视图的背景色作为状态栏颜色
 * @param v 提取该View的背景颜色设置为状态栏颜色, 如果该View没有背景颜色则该函数调用无效
 * @param darkMode 是否显示暗色状态栏文字颜色
 */
@JvmOverloads
fun Activity.immersive(v: View, darkMode: Boolean? = null) {
    val background = v.background
    if (background is ColorDrawable) {
        immersive(background.color, darkMode)
    }
}

/**
 * 设置透明状态栏或者状态栏颜色, 此函数会导致状态栏覆盖界面,
 * 如果不希望被状态栏遮挡Toolbar请再调用[statusPadding]设置视图的paddingTop 或者 [statusMargin]设置视图的marginTop为状态栏高度
 *
 * 如果不指定状态栏颜色则会应用透明状态栏(全屏属性), 会导致键盘遮挡输入框
 *
 * @param color 状态栏颜色, 不指定则为透明状态栏
 * @param darkMode 是否显示暗色状态栏文字颜色
 */
@JvmOverloads
fun Activity.immersive(@ColorInt color: Int = COLOR_TRANSPARENT, darkMode: Boolean? = null) {
    when (color) {
        COLOR_TRANSPARENT -> {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.statusBarColor = color
        }

        else -> {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
        }
    }
    if (darkMode != null) {
        darkMode(darkMode)
    }
}

/**
 * 退出沉浸式状态栏并恢复默认状态栏颜色
 *
 * @param black 是否显示黑色状态栏白色文字(不恢复状态栏颜色)
 */
@JvmOverloads
fun Activity.immersiveExit(black: Boolean = false) {
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.decorView.systemUiVisibility =
        (View.SYSTEM_UI_FLAG_LAYOUT_STABLE and View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

    // 恢复默认状态栏颜色
    if (black) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    } else {
        val typedArray = obtainStyledAttributes(intArrayOf(android.R.attr.statusBarColor))
        window.statusBarColor = typedArray.getColor(0, 0)
        typedArray.recycle()
    }
}

/**
 * 获取颜色资源值来设置状态栏
 */
@JvmOverloads
fun Activity.immersiveRes(@ColorRes color: Int, darkMode: Boolean? = null) =
    immersive(resources.getColor(color, null), darkMode)


/**
 * 开关状态栏暗色模式, 并不会透明状态栏, 只是单纯的状态栏文字变暗色调.
 *
 * @param darkMode 状态栏文字是否为暗色
 */
@JvmOverloads
fun Activity.darkMode(darkMode: Boolean = true) {
    var systemUiVisibility = window.decorView.systemUiVisibility
    systemUiVisibility = if (darkMode) {
        systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
        systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
    }
    window.decorView.systemUiVisibility = systemUiVisibility
}


/**
 * 增加View的paddingTop, 增加高度为状态栏高度, 用于防止视图和状态栏重叠
 * 如果是RelativeLayout设置padding值会导致centerInParent等属性无法正常显示
 * @param remove 如果默认paddingTop大于状态栏高度则添加无效, 如果小于状态栏高度则无法删除
 */
@JvmOverloads
fun View.statusPadding(remove: Boolean = false) {
    if (this is RelativeLayout) {
        throw UnsupportedOperationException("Unsupported set statusPadding for RelativeLayout")
    }
    val statusBarHeight = context.statusBarHeight
    val lp = layoutParams
    if (lp != null && lp.height > 0) {
        lp.height += statusBarHeight //增高
    }
    if (remove) {
        if (paddingTop < statusBarHeight) return
        setPadding(
            paddingLeft, paddingTop - statusBarHeight, paddingRight, paddingBottom
        )
    } else {
        if (paddingTop >= statusBarHeight) return
        setPadding(
            paddingLeft, paddingTop + statusBarHeight, paddingRight, paddingBottom
        )
    }
}

/**
 * 增加View的marginTop值, 增加高度为状态栏高度, 用于防止视图和状态栏重叠
 * @param remove 如果默认marginTop大于状态栏高度则添加无效, 如果小于状态栏高度则无法删除
 */
@JvmOverloads
fun View.statusMargin(remove: Boolean = false) {
    val statusBarHeight = context.statusBarHeight
    val lp = layoutParams as ViewGroup.MarginLayoutParams
    if (remove) {
        if (lp.topMargin < statusBarHeight) return
        lp.topMargin -= statusBarHeight
        layoutParams = lp
    } else {
        if (lp.topMargin >= statusBarHeight) return
        lp.topMargin += statusBarHeight
        layoutParams = lp
    }
}


/**
 * 创建假的透明栏
 */
private fun Context.setTranslucentView(container: ViewGroup, color: Int) {
    var simulateStatusBar: View? = container.findViewById(android.R.id.custom)
    if (simulateStatusBar == null && color != 0) {
        simulateStatusBar = View(container.context)
        simulateStatusBar.id = android.R.id.custom
        val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight)
        container.addView(simulateStatusBar, lp)
    }
    simulateStatusBar?.setBackgroundColor(color)
}


/**
 * 设置ActionBar的背景颜色
 */
fun AppCompatActivity.setActionBarBackground(@ColorInt color: Int) {
    supportActionBar?.setBackgroundDrawable(ColorDrawable(color))
}

fun AppCompatActivity.setActionBarBackgroundRes(@ColorRes color: Int) {
    supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(color)))
}

/**
 * 设置ActionBar的背景颜色为透明
 */
fun AppCompatActivity.setActionBarTransparent() {
    supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
}


/**
 * 显示或隐藏导航栏, 系统开启可以隐藏, 系统未开启不能开启
 *
 * @param enabled 是否显示导航栏
 */
@JvmOverloads
fun Activity.setNavigationBar(enabled: Boolean = true) {
    val systemUiVisibility = window.decorView.systemUiVisibility
    if (enabled) {
        window.decorView.systemUiVisibility =
            systemUiVisibility and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION and View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    } else {
        window.decorView.systemUiVisibility =
            systemUiVisibility or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }
}

/**
 * 设置是否全屏
 *
 * @param enabled 是否全屏显示
 */
@JvmOverloads
fun Activity.setFullscreen(enabled: Boolean = true) {
    val systemUiVisibility = window.decorView.systemUiVisibility
    window.decorView.systemUiVisibility = if (enabled) {
        systemUiVisibility or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    } else {
        systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE and View.SYSTEM_UI_FLAG_FULLSCREEN.inv()
    }
}

/**
 * 是否有导航栏
 */
val Activity?.isNavigationBar: Boolean
    get() {
        this ?: return false
        val vp = window.decorView as? ViewGroup
        if (vp != null) {
            for (i in 0 until vp.childCount) {
                if (vp.getChildAt(i).id != NO_ID && "navigationBarBackground" == resources.getResourceEntryName(
                        vp.getChildAt(i).id
                    )
                ) return true
            }
        }
        return false
    }

/**
 * 如果当前设备存在导航栏返回导航栏高度, 否则0
 */
val Context?.navigationBarHeight: Int
    get() {
        this ?: return 0
        val resourceId: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        var height = 0
        if (resourceId > 0) {
            height = resources.getDimensionPixelSize(resourceId)
        }
        return height
    }


/**
 * 状态栏高度
 */
val Context?.statusBarHeight: Int
    get() {
        this ?: return 0
        var result = 24
        val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
        result = if (resId > 0) {
            resources.getDimensionPixelSize(resId)
        } else {
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, result.toFloat(), Resources.getSystem().displayMetrics
            ).toInt()
        }
        return result
    }
