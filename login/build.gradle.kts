@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.xeonyu.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.android)
}


android {
    namespace = "com.yzq.login"

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}


dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.androidx.vectordrawable)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.xeonyu.coroutine)
    implementation(libs.xeonyu.binding)
    implementation(libs.xeonyu.logger)
    implementation(libs.androidx.activity)

    ksp(libs.therouter.apt)


    implementation(project(":base-ui"))
    implementation(project(":resource"))
    implementation(project(":dialog"))
    implementation(project(":widget"))
    implementation(project(":soft-input"))
    implementation(project(":util"))
    implementation(project(":router"))

}

