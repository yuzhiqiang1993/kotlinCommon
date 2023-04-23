import com.yzq.build_manager.AndroidConfig
import com.yzq.build_manager.AndroidOfficial
import com.yzq.build_manager.ThirdParty

plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.yzq.materialdialog"
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
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(AndroidOfficial.appcompat)
    implementation(AndroidOfficial.material)
    implementation(AndroidOfficial.constraintlayout)

    api(ThirdParty.materialDialogsCore)
    api(ThirdParty.materialDialogsInput)
    api(ThirdParty.materialDialogsBottomsheets)
    api(ThirdParty.materialDialogsLifecycle)

    api(ThirdParty.dateTimePicker)
}
