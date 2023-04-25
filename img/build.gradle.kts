plugins {
    id("com.yzq.android.library")
}

android {
    namespace = "com.yzq.img"

    buildTypes {
        release {
            consumerProguardFiles("proguard-rules.pro")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(libs.androidx.appcompat)

    api(libs.photoView)
    api(libs.coil)

    implementation(libs.utilcodex)
}