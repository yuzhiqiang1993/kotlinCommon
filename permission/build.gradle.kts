@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.xeonyu.library)
}


android {
    namespace = "com.yzq.permission"
    buildFeatures {
        buildConfig = true
    }
}


dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(project(":dialog"))
    //    implementation(project(":application"))

    implementation(libs.androidx.appcompat)
    /*xxPermission*/
    api(libs.xxPermissions)
    implementation(libs.xeonyu.logger)

}
