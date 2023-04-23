import com.yzq.build_manager.AndroidConfig
import com.yzq.build_manager.AndroidOfficial
import com.yzq.build_manager.Kotlinx

plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.yzq.coroutine"
    compileSdk = AndroidConfig.compileSdkVersion

    defaultConfig {
        minSdk = AndroidConfig.minSdkVersion
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

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
