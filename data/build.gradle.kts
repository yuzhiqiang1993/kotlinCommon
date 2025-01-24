plugins {
    alias(libs.plugins.xeonyu.library)
}


android {
    namespace = "com.yzq.data"
    buildFeatures {
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.moshiKotlin)
    implementation(libs.brv)
}