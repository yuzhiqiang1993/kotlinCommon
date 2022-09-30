package com.yzq.common.constants

import android.annotation.SuppressLint
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PathUtils
import com.yzq.lib_application.AppContext
import java.io.File

/**
 * @description:App相关路径常量
 * @author : yzq
 * @date   : 2018/3/9
 * @time   : 11:19
 *
 */

object StoragePath {


    /*项目路径 /storage/emulated/0/Android/data/com.yzq.kotlincommon*/
    private val eternalAppDataPath = "${PathUtils.getExternalAppDataPath()}${File.separator}"

    /*项目文件路径  /storage/emulated/0/Android/data/com.yzq.kotlincommon/files */
    private val externalAppFilesPath = "${PathUtils.getExternalAppFilesPath()}${File.separator}"

    /*项目图片路径  /storage/emulated/0/Android/data/com.yzq.kotlincommon/files/Pictures/ */
    val externalAppPicturesPath = "${PathUtils.getExternalAppPicturesPath()}${File.separator}"

    /*项目图片路径  /storage/emulated/0/Android/data/com.yzq.kotlincommon/files/files/ */
    val externalAppFilePath = "${PathUtils.getExternalAppFilesPath()}${File.separator}"


    @SuppressLint("UsableSpace")
    fun logPathInfo() {
        LogUtils.i(
            """
                eternalAppDataPath：$eternalAppDataPath,
                externalAppFilesPath：$externalAppFilesPath,       
                externalAppPicturesPath：$externalAppPicturesPath,
                AppContext.filesDir.path.usableSpace: ${AppContext.filesDir.usableSpace}
                AppContext.cacheDir.path: ${AppContext.cacheDir.path}
                AppContext.getExternalFilesDir.path.usableSpace: ${
                AppContext.getExternalFilesDir(
                    null
                )?.usableSpace
            }
                
            """.trimIndent()
        )
    }


}
