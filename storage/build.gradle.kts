@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.xeonyu.library)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.yzq.storage"

    defaultConfig {
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    implementation(libs.xeonyu.application)
    implementation(libs.xeonyu.logger)

    api(libs.xeonyu.mmkv)
    api(libs.threetenabp)

    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

}