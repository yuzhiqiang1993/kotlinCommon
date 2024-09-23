@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.xeonyu.library)
    alias(libs.plugins.kotlin.android)
}


android {
    namespace = "com.yzq.soft_input"

    buildFeatures {
        viewBinding = true
    }
}


dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.xeonyu.coroutine)
    implementation(libs.xeonyu.logger)
    implementation(libs.xeonyu.binding)

}

