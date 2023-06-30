package com.yzq.storage

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PathUtils
import java.io.File

/**
 * @description: App存储
 *  https://blog.csdn.net/unreliable_narrator/article/details/127250034
 *  Android的存储分为内部存储和外部存储
 *  早期的手机设备内部存储较小，都是通过插拔sd卡挂载外部存储的，后来的设备都是直接在手机内部集成一款容量较大的存储，将其划分为内部存储和外部存储。
 *  在Android10之前，没有强制做分区存储，导致每个app都获取到读写外部存储权限之后就能随意操作根目录，每个app一般都是创建一个自用的文件夹，往里面存数据，但是公共目录的数据是不会随着app的卸载而被清除的，
 *  这就导致了垃圾文件越来越多，且不好管理。还有一个就就是隐私安全得不到保障，我的创建的公共目录其他app也能访问，安全性不高。
 *  因此，在Android10的时候就增加了分区存储的特性，同时对权限做了进一步的收紧（沙盒模式），App只能访问自己目录下的文件和公共媒体文件，可以读写自己创建的所有文件，删除其他App创建的媒体类文件则需要用户授权，无法删除其他App创建的非媒体文件
 * @author : yzq
 * @date   : 2018/3/9
 * @time   : 11:19
 *
 */

object AppStorage {


    /**
     * Internal  内部存储
     * 内部存储是用来存放Android系统本身以及应用程序的区域，例如 /system/目录，/data/目录，通常不会很大
     * /data/data/package/ 是系统提供给应用程序的内部存储空间，像SharedPreferences，Sqlite,缓存文件等都是保存在该目录下，该目录是私有的，只能有app自身访问(无需申请权限)，app卸载时会被移除掉
     * 注意：如果设备root了，那么用户是可能访问的
     * 内部存储的路径:/data/data/包名
     * @constructor Create empty Internal
     */
    object Internal {
        val dataPath = "${PathUtils.getInternalAppDataPath()}${File.separator}"
        val filesPath = "${PathUtils.getInternalAppFilesPath()}${File.separator}"
        val spPath = "${PathUtils.getInternalAppSpPath()}${File.separator}"
        val cachePath = "${PathUtils.getInternalAppCachePath()}${File.separator}"
        val dbPath = "${PathUtils.getInternalAppDbsPath()}${File.separator}"

    }


    /**
     * External 外部存储
     * 外部存储可分为公共目录和App私有目录，App操作私有目录同样不需要读写权限，但是操作公共目录则需要读写权限。
     * 外部私有目录也会随着app的卸载被清除掉，如果希望app被删除后数据任然保留，则应放在公共目录
     * @constructor Create empty External
     */
    object External {
        /**
         * Private 私有目录
         * 不需要权限即可操作，会随着app的卸载被删除
         * @constructor Create empty Private
         */
        object Private {
            /**
             * root path 私有根目录
             * /storage/emulated/0/Android/data/package/
             */
            val rootPath = "${PathUtils.getExternalAppDataPath()}${File.separator}"

            /**
             * Files path  文件目录
             * /storage/emulated/0/Android/data/package/files
             */
            val filesPath = "${PathUtils.getExternalAppFilesPath()}${File.separator}"


            /**
             * Download path  下载目录
             * /storage/emulated/0/Android/data/package/files/Download
             */
            val downloadPath = "${PathUtils.getExternalAppDownloadPath()}${File.separator}"

            /**
             * Pictures path 图片目录
             * /storage/emulated/0/Android/data/package/files/Pictures/
             */
            val picturesPath = "${PathUtils.getExternalAppPicturesPath()}${File.separator}"

            /**
             * cache path 缓存目录
             * /storage/emulated/0/Android/data/package/cache/
             */
            val cachePath = "${PathUtils.getExternalAppCachePath()}${File.separator}"

        }

        /**
         * Public 公共目录
         * 操作需要读写权限，App卸载时不会不会被删除，一般存放跟app生命周期无关的数据，例如相机拍摄的图片不会随着相机app的删除而丢失。
         * 在Android10之前,
         *
         * @constructor Create empty Publich
         */
        object Public {

            /**
             * Root path 根目录
             * /storage/emulated/0/
             *  Android 10之后是不能读写的，主要是为了解决各个App随意创建目录的问题，只能访问官方指定的公共目录
             */
            val rootPath = "${PathUtils.getExternalStoragePath()}${File.separator}"

            /**
             * Download path 下载目录
             * /storage/emulated/0/Download
             */
            val downloadPath = "${PathUtils.getExternalDownloadsPath()}${File.separator}"


            /**
             * Pictures path  所有的图片 (不包括那些用照相机拍摄的照片)
             * /storage/emulated/0/Pictures/
             */
            val picturesPath = "${PathUtils.getExternalPicturesPath()}${File.separator}"

            /**
             * Movies path 所有的电影 (不包括那些用摄像机拍摄的视频) 和 Download / 其他下载的内容
             * /storage/emulated/0/Movies
             */
            val moviesPath = "${PathUtils.getExternalMoviesPath()}${File.separator}"

            /**
             * Dcim path 数字相机拍摄的照片
             * /storage/emulated/0/DCIM/
             */
            val dcimPath = "${PathUtils.getExternalDcimPath()}${File.separator}"

            /**
             * Music path 用户音乐
             * /storage/emulated/0/Music/
             */
            val musicPath = "${PathUtils.getExternalMusicPath()}${File.separator}"

            /**
             * Podcasts path 音频 / 视频的剪辑片段
             * /storage/emulated/0/Music/
             */
            val podcastsPath = "${PathUtils.getExternalAppPodcastsPath()}${File.separator}"

            /**
             * Ringtones path 铃声
             * /storage/emulated/0/Ringtones/
             */
            val ringtonesPath = "${PathUtils.getExternalRingtonesPath()}${File.separator}"

            /**
             * Alarms path 闹钟
             * /storage/emulated/0/Alarms/
             */
            val alarmsPath = "${PathUtils.getExternalAlarmsPath()}${File.separator}"
        }
    }


    fun logPathInfo() {

        val pathInfo = """
            内部存储
            dataPath:${Internal.dataPath}
            filesPath:${Internal.filesPath}
            cachePath:${Internal.cachePath}
            dbPath:${Internal.dbPath}
            spPath:${Internal.spPath}
            --------------------------
            外部存储
                私有目录
                 dataPath:${External.Private.rootPath}
                 filesPath:${External.Private.filesPath}
                 picturesPath:${External.Private.picturesPath}
                 cachePath:${External.Private.cachePath}
                 ....
                 公共目录
                  rootPath:${External.Public.rootPath}
                  picturesPath:${External.Public.picturesPath}
                  moviesPath:${External.Public.moviesPath}
        """.trimIndent()


        LogUtils.i(pathInfo)

    }
}
