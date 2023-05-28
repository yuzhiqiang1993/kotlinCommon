plugins {
    id("com.yzq.android.library")
}

android {
    namespace = "com.yzq.img"


}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(libs.androidx.appcompat)

    api(libs.photoView)
    api(libs.coil)

    implementation(libs.utilcodex)
}
