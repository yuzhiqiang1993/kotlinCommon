@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.xeonyu.library)
}


android {
    namespace = "com.yzq.permission"
}


dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(project(":materialdialog"))
//    implementation(project(":application"))

    implementation(libs.androidx.appcompat)
    /*utils*/
    implementation(libs.utilcodex)
    /*xxPermission*/
    api(libs.xxPermissions)
    implementation(libs.xeonyu.logger)

}
