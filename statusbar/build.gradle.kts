import com.yzq.build_manager.AndroidOfficial

plugins {
    id("com.yzq.android.library")

}


android {
    namespace = "com.yzq.statusbar"

    buildTypes {
        release {
            consumerProguardFiles("proguard-rules.pro")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(AndroidOfficial.appcompat)
    implementation(AndroidOfficial.corektx)

}