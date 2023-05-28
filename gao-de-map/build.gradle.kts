plugins {
    id("com.yzq.android.library")
    id("com.yzq.theRouter")
}

android {
    namespace = "com.yzq.gao_de_map"


    buildFeatures {
        viewBinding = true
    }

}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(project(":common"))
    api("com.amap.api:location:6.3.0")
}
