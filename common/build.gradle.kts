import com.yzq.build_manager.AndroidOfficial
import com.yzq.build_manager.ThirdParty

plugins {
    id("com.yzq.android.library")
    id("com.yzq.android.room")
    kotlin("kapt")
}

android {
    namespace = "com.yzq.common"


    buildTypes {
        release {
            consumerProguardFiles("proguard-rules.pro")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))

    /*处理图片方向*/
    implementation(AndroidOfficial.exifinterface)

    /*flexbox*/
    api(ThirdParty.flexboxLayout)

    /*内存泄漏检测 leakcanary*/
    debugImplementation(ThirdParty.leakcanaryAndroid)

    api(project(":base"))
    api(project(":mmkv"))
}
