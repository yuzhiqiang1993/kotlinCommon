import com.yzq.dependency_manager_plugin.AndroidConfig
import com.yzq.dependency_manager_plugin.AndroidOfficial
import com.yzq.dependency_manager_plugin.ThirdParty

plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.yzq.binding"
    compileSdk = AndroidConfig.compileSdkVersion

    defaultConfig {
        minSdk = AndroidConfig.minSdkVersion
    }

    buildTypes {
        release {
            consumerProguardFiles("proguard-rules.pro")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(AndroidOfficial.appcompat)
    implementation(AndroidOfficial.corektx)
    implementation(AndroidOfficial.lifecycle)
    implementation(ThirdParty.utilcode)
}
