@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.xeonyu.library)
    alias(libs.plugins.ksp)
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
//    api(libs.gaode.location)

    api(libs.xeonyu.location.manager)
    api(libs.xeonyu.location.gaode)
    ksp(libs.therouter.apt)
}
