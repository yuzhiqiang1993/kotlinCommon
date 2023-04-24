plugins {
    id("com.yzq.android.library")
}


android {
    namespace = "com.yzq.permission"
    buildTypes {
        release {
            consumerProguardFiles("proguard-rules.pro")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

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


}
