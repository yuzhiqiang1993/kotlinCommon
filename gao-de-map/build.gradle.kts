plugins {
    id("com.yzq.android.library")
    id("com.yzq.theRouter")
}

android {
    namespace = "com.yzq.gao_de_map"
    buildTypes {
        release {
            consumerProguardFiles("proguard-rules.pro")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
    }

}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(project(":common"))
    api("com.amap.api:location:6.3.0")
}
