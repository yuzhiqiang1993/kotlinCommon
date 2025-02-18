@Suppress("DSL_SCOPE_VIOLATION") plugins {
    alias(libs.plugins.xeonyu.library)
}

android {
    namespace = "com.yzq.baseui"
    buildTypes {
        release {
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.androidx.appcompat)
//    implementation(libs.androidx.constraintlayout)

//    implementation(libs.xeonyu.logger)

}
