plugins {
    id("com.yzq.android.library")
}

android {
    namespace = "com.yzq.application"
    buildTypes {
        release {

            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(libs.androidx.appcompat)
    implementation(libs.utilcodex)
}
