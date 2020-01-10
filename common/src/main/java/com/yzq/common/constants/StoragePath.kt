package com.yzq.common.constants

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


    /*项目路径 /storage/emulated/0/Android/data/com.yzq.kotlincommon*/
    val eternalAppDataPath = "${PathUtils.getExternalAppDataPath()}${File.separator}"
    /*项目文件路径  /storage/emulated/0/Android/data/com.yzq.kotlincommon/files */
    val externalAppFilesPath = "${PathUtils.getExternalAppFilesPath()}${File.separator}"

    /*项目图片路径  /storage/emulated/0/Android/data/com.yzq.kotlincommon/files/Pictures/ */
    val externalAppPicturesPath = "${PathUtils.getExternalAppPicturesPath()}${File.separator}"

    fun logPathInfo() {
        LogUtils.i(
            """
                eternalAppDataPath：$eternalAppDataPath,
                externalAppFilesPath：$externalAppFilesPath,       
                externalAppPicturesPath：$externalAppPicturesPath,
            """.trimIndent()
        )
    }


}
