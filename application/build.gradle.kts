plugins {
    id("com.yzq.android.library")
}

android {
    namespace = "com.yzq.application"

}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(libs.androidx.appcompat)
    implementation(libs.utilcodex)
}
