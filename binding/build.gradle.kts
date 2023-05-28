plugins {
    id("com.yzq.android.library")
}

android {
    namespace = "com.yzq.binding"

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.utilcodex)
}
