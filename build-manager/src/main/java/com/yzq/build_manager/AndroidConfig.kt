package com.yzq.build_manager

/**
 * @description Android 相关配置
 * @author yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date 2022/10/24
 * @time 16:42
 */

object AndroidConfig {
    /**
     * Compile sdk version
     * 按照指定sdk版本进行编译  不影响实际运行  尽量写最新的，最好跟targetsdkversion版本保持一致
     */
    const val compileSdkVersion = 33

    /**
     * Min sdk version
     * App支持的最低版本
     */
    const val minSdkVersion = 21 // Android 5.1

    /**
     * Target sdk version
     * 目标版本 启用该版本特性 主要做兼容处理 低于该版本的设备按照实际版本运行  高于该版本的设备按照该版本特性运行
     * 可以参考市场占有份额来定 https://www.appbrain.com/stats/top-android-sdk-versions
     */
    const val targetSdkVersion = 33 // Android 13

    /**
     * Version code
     * 软件版本号的形式是major.minor.maintenance
     * major是主版本号，⼀般在软件有重⼤升级时增长
     * minor是次版本号，⼀般在软件有新功能时增长
     * maintenance是维护版本，⼀般在软件有主要的问题修复后增长
     * 正式版本: major.minor.maintenance----1.0.0
     * 1.0.0对应   10000
     * 1.0.1对应   10001
     * 1.0.10对应  10010
     * 1.1.99对应  10199
     * 1.10.0对应  11000
     * 1.12.9对应  11209
     * 1.12.10对应 11210
     * 10.0.0对应  100000
     * 10.1.1对应  100101
     * 10.10.10对应101010
     *
     */
    const val versionCode = 10001

    /**
     * Version name
     * 显示给用户看的版本
     */
    const val versionName = "1.0.1"
    const val multiDexEnabled = true
}
