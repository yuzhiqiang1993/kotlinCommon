@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.xeonyu.library)
}

android {
    namespace = "com.yzq.common"

    buildFeatures {
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))

    /*处理图片方向*/
    implementation(libs.androidx.exifinterface)

    /*flexbox*/
    api(libs.flexbox.layout)

    /*内存泄漏检测 leakcanary*/
    debugImplementation(libs.leakcanary.android)

    api(project(":base"))
    api(project(":storage"))


}
