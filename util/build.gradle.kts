@Suppress("DSL_SCOPE_VIOLATION") plugins {
    alias(libs.plugins.xeonyu.library)
}

android {
    namespace = "com.yzq.util"
    buildTypes {
        release {
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
//
//    buildFeatures {
//        viewBinding = true
//    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(libs.moshiKotlin)
    api(libs.xeonyu.application)
    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)
}
