

plugins {
    id("com.yzq.android.library")

}


android {
    namespace = "com.yzq.statusbar"


}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)

}