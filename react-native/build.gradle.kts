plugins {
    alias(libs.plugins.xeonyu.library)
}

android {
    namespace = "com.yzq.react_native"
    compileSdk = 34

//    packaging {
//        jniLibs.pickFirsts.apply {
//            add("lib/arm64-v8a/libc++_shared.so")
//            add("lib/armeabi-v7a/libc++_shared.so")
//            add("lib/x86/libc++_shared.so")
//            add("lib/x86_64/libc++_shared.so")
//        }
//    }
}

dependencies {

    api(libs.react.android)
    api(libs.react.hermes.android)
    implementation(project(":base"))

}