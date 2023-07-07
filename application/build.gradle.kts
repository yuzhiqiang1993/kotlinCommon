@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.xeonyu.library)
}

android {
    namespace = "com.yzq.application"

}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(libs.androidx.appcompat)
    implementation(libs.xeonyu.logger)
}
