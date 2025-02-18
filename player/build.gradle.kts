plugins {
    alias(libs.plugins.xeonyu.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.yzq.player"
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.vectordrawable)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.xeonyu.coroutine)
    implementation(libs.xeonyu.binding)
    implementation(libs.androidx.activity)
    api(libs.gsyvideoplayer)

    ksp(libs.therouter.apt)

    implementation(project(":base-ui"))
    implementation(project(":router"))

}