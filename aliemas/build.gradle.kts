@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.xeonyu.library)
}

android {
    namespace = "com.yzq.aliemas"

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(libs.utilcodex)


    //崩溃分析
    api(libs.alicloud.android.ha.crashreporter)
    //移动日志
    api(libs.alicloud.android.tlog)
    //性能监控
    api(libs.alicloud.android.apm)
    //移动分析  埋点 部分arm64-v8a设备上报错： https://help.aliyun.com/document_detail/65275.html
//    api 'com.aliyun.ams:alicloud-android-man:1.2.7'
    //推送
    api(libs.alicloud.android.push)
    //公共依赖库
    api(libs.alicloud.android.utdid)
    api(libs.alicloud.android.utils)
    api(libs.alicloud.android.ut)
    api(libs.alicloud.android.beacon)
    api(libs.alicloud.android.ha.adapter)
}
