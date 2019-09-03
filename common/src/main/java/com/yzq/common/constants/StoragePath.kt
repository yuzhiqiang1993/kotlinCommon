package com.yzq.common.constants

import android.os.Environment
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PathUtils
import java.io.File

/**
 * @description:App相关路径常量
 * @author : yzq
 * @date   : 2018/3/9
 * @time   : 11:19
 *
 */

object StoragePath {


    /*手机存储根路径*/
    val ROOT_PATH = "${Environment.getExternalStorageDirectory().absolutePath}${File.separator}"
    /*共享路径*/
    val path = "${PathUtils.getExternalAppFilesPath()}"
    /*项目总路径*/

    val PROJECT_PATH = PathUtils.getExternalAppDataPath()
    /*包名路径*/
    // val APP_PATH = "$PROJECT_PATH${AppUtils.getAppPackageName()}${File.separator}"
    val APP_PATH = PathUtils.getExternalAppDataPath()
    /*图片路径*/
    val PICTURE_PATH = "${APP_PATH}picture${File.separator}"

    fun logPathInfo() {
        LogUtils.i(
            """
                根路径：$ROOT_PATH,
                项目总路径：$PROJECT_PATH,
                包名路径：$APP_PATH,
                图片路径：$PICTURE_PATH

            """.trimIndent()
        )
    }


}
