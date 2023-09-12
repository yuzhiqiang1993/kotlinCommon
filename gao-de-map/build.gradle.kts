@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.xeonyu.library)
//    alias(libs.plugins.ksp)
    kotlin("kapt")
}

android {
    namespace = "com.yzq.gao_de_map"

    buildFeatures {
        viewBinding = true
    }

}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(project(":common"))
    api(libs.gaode.location)

    kapt(libs.therouter.apt)
}
