@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.xeonyu.library)
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
    implementation(libs.xeonyu.logger)
}
