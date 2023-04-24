plugins {
    id("com.yzq.android.library")
}

android {
    namespace = "com.yzq.mmkv"

    buildTypes {
        release {
            consumerProguardFiles("proguard-rules.pro")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    api(libs.mmkv)
}
