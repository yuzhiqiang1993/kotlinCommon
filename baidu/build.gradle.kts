plugins {
    id("com.yzq.android.library")
}

android {
    namespace = "com.xeon.baidu"
}

dependencies {
    api(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(libs.androidx.appcompat)
}