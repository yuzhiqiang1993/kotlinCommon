import com.yzq.build_manager.AndroidOfficial
import com.yzq.build_manager.Kotlinx

plugins {
    id("com.yzq.android.library")
}

android {
    namespace = "com.yzq.coroutine"


    buildTypes {
        release {
            consumerProguardFiles("proguard-rules.pro")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    api(Kotlinx.coroutinesCore)
    api(Kotlinx.coroutineAndroid)

    implementation(AndroidOfficial.lifecycle)
    implementation(AndroidOfficial.fragmentKtx)
}
