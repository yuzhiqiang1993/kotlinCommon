@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.xeonyu.library)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.yzq.storage"
}

dependencies {
    implementation(libs.utilcodex)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    implementation(libs.xeonyu.application)
    implementation(libs.xeonyu.logger)

    api(project(":mmkv"))
    api(libs.threetenabp)

    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

}