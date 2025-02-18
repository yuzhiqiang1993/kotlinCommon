@Suppress("DSL_SCOPE_VIOLATION") plugins {
    alias(libs.plugins.xeonyu.library)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.yzq.amap"

    buildFeatures {
        viewBinding = true
    }

}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
//    implementation(project(":common"))

    api(libs.xeonyu.location.manager)
    api(libs.xeonyu.location.gaode)
    ksp(libs.therouter.apt)

    implementation(project(":base"))
    implementation(project(":router"))
    implementation(project(":base-ui"))
}
