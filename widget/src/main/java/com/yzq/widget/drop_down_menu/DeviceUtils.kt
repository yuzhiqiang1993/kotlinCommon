package com.yzq.widget.drop_down_menu

import android.content.Context
import android.graphics.Point
import android.view.WindowManager
import com.blankj.utilcode.util.Utils


object DeviceUtils {
    /**
     * 获取屏幕尺寸
     */
    @JvmStatic
    fun getScreenHeight(context: Context): Int {

        val wm = Utils.getApp().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        wm.defaultDisplay.getRealSize(point)
        return point.y

    }
}