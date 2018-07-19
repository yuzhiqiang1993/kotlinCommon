package com.yzq.common.constants

import android.os.Environment

import com.blankj.utilcode.util.AppUtils

import java.io.File

/**
 * Created by yzq on 2018/3/9.
 * 项目路径
 */

class ProjectPath {

    companion object {
        /*根路径*/
        val ROOT_PATH = Environment.getExternalStorageDirectory().absolutePath + File.separator + ".ESP" + File.separator
        /*项目路径*/
        val PROJECT_PATH = ROOT_PATH + "." + AppUtils.getAppPackageName() + File.separator
        /*图片路径*/
        val PICTURE_PATH = PROJECT_PATH + ".picture" + File.separator
    }

}
