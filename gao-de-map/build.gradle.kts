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

    api(libs.xeonyu.location.manager)
    api(libs.xeonyu.location.gaode)
    implementation(libs.xeonyu.binding)
    implementation(libs.xeonyu.logger)
    implementation(libs.toaster)
    ksp(libs.therouter.apt)

    implementation(project(":widget"))
    implementation(project(":router"))
    implementation(project(":util"))
//    implementation(project(":dialog"))
//    implementation(project(":base-ui"))
    implementation(project(":biz-core"))
    implementation(project(":permission"))

}
