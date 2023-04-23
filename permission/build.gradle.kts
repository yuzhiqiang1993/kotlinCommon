import com.yzq.build_manager.AndroidConfig
import com.yzq.build_manager.ThirdParty

plugins {
    id("com.android.library")
    kotlin("android")
}


android {
    namespace = "com.yzq.permission"
    compileSdk = AndroidConfig.compileSdkVersion

    defaultConfig {
        minSdk = AndroidConfig.minSdkVersion
        multiDexEnabled = AndroidConfig.multiDexEnabled
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        release {
            consumerProguardFiles("proguard-rules.pro")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    lint {
        abortOnError = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}


dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(project(":materialdialog"))
    implementation(project(":application"))

    /*utils*/
    implementation(ThirdParty.utilcode)
    /*xxPermission*/
    api(ThirdParty.xxPermission)


}
