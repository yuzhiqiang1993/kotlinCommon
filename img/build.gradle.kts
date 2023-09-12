@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.xeonyu.library)
}

android {
    namespace = "com.yzq.img"


}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(libs.androidx.appcompat)
    implementation(libs.xeonyu.application)

    api(libs.photoView)
    api(libs.coil)
    api(libs.coil.gif)
    api(libs.coil.svg)

    implementation(libs.utilcodex)
}
