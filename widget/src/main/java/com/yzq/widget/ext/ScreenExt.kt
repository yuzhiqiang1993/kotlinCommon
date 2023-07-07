import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager


/**
 * 获取屏幕宽高
 * @receiver Context
 * @return Pair<Int, Int>
 */
private fun Context.getScreenSize(): Pair<Int, Int> {
    val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = windowManager.defaultDisplay

    val displayMetrics = DisplayMetrics()
    display.getRealMetrics(displayMetrics)

    val widthPixels = displayMetrics.widthPixels
    val heightPixels = displayMetrics.heightPixels

    return Pair(widthPixels, heightPixels)
}

/**
 * 获取屏幕高度
 * @receiver Context
 * @return Int
 */
fun Context.getScreenHeight(): Int {
    return getScreenSize().second
}

/**
 * 获取屏幕宽度
 * @receiver Context
 * @return Int
 */
fun Context.getScreenWidth(): Int {
    return getScreenSize().first
}