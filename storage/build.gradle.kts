@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.xeonyu.library)
//    alias(libs.plugins.ksp)
    kotlin("kapt")
}

android {
    namespace = "com.yzq.storage"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    implementation(libs.xeonyu.application)
    implementation(libs.xeonyu.logger)

    api(libs.xeonyu.mmkv)
    api(libs.threetenabp)

    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

}