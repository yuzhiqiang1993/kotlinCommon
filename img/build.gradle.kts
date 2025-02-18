@Suppress("DSL_SCOPE_VIOLATION") plugins {
    alias(libs.plugins.xeonyu.library)
}

android {
    namespace = "com.yzq.img"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.viewpager2)

    implementation(libs.xeonyu.application)
    implementation(libs.xeonyu.binding)

    implementation(libs.photoView)
    api(libs.coil)
    api(libs.coil.gif)
    api(libs.coil.svg)

    implementation(project(":base-ui"))
    implementation(project(":util"))

}
