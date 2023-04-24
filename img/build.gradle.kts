import com.yzq.build_manager.AndroidOfficial
import com.yzq.build_manager.ThirdParty

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
    implementation(AndroidOfficial.appcompat)

    api(ThirdParty.photoview)
    api(ThirdParty.coil)

    implementation(ThirdParty.utilcode)
}
