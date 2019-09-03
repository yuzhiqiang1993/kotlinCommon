package com.yzq.lib_widget.drop_down_menu;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

/**
 * 设备工具箱，提供与设备硬件相关的工具方法
 */
public class DeviceUtils {

    /**
     * 获取屏幕尺寸
     */

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static int getSceenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        if (wm == null) {
            return -1;
        }
        Point point = new Point();
        wm.getDefaultDisplay().getRealSize(point);
        return point.y;

    }


}