package com.xeon.bsdiff

import com.blankj.utilcode.util.LogUtils

object BsDiffUtil {
    /**
     * 生成差分包
     *
     * @param oldFile
     * @param newFile
     * @param patchFile
     * @return
     */
    external fun fileDiff(oldFile: String?, newFile: String?, patchFile: String?): Int
    /**
     * 将老包与差分包合并成新包
     *
     * @param oldFile
     * @param newFile
     * @param patchFile
     * @return
     */
    external fun fileCombine(oldFile: String?, newFile: String?, patchFile: String?): Int

    init {
        try {
            System.loadLibrary("bsdiff")
            System.loadLibrary("bspatch")
        } catch (e: Throwable) {
            e.printStackTrace()
            LogUtils.e("load so error")
        }
    }
}