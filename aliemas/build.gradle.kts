import com.yzq.build_manager.ThirdParty

plugins {
    id("com.yzq.android.library")
}

android {
    namespace = "com.yzq.aliemas"
    buildTypes {
        release {
            consumerProguardFiles("proguard-rules.pro")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(ThirdParty.utilcode)


    //崩溃分析
    api("com.aliyun.ams:alicloud-android-ha-crashreporter:1.3.0")
    //移动日志
    api("com.aliyun.ams:alicloud-android-tlog:1.1.4.5-open")
    //性能监控
    api("com.aliyun.ams:alicloud-android-apm:1.1.1.0-open")
    //移动分析  埋点 部分arm64-v8a设备上报错： https://help.aliyun.com/document_detail/65275.html
//    api 'com.aliyun.ams:alicloud-android-man:1.2.7'
    //推送
    api("com.aliyun.ams:alicloud-android-push:3.8.3")
    //公共依赖库
    api("com.aliyun.ams:alicloud-android-utdid:2.6.0")
    api("com.aliyun.ams:alicloud-android-utils:2.0.0")
    api("com.aliyun.ams:alicloud-android-ut:5.4.5")
    api("com.aliyun.ams:alicloud-android-beacon:1.0.8")
    api("com.aliyun.ams:alicloud-android-ha-adapter:1.1.5.4-open")
}
