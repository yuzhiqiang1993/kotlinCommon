import com.yzq.dependency_manager_plugin.AndroidConfig
import com.yzq.dependency_manager_plugin.AndroidOfficial
import com.yzq.dependency_manager_plugin.ThirdParty

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.yzq.common"
    compileSdk = AndroidConfig.compileSdkVersion

    defaultConfig {
        minSdk = AndroidConfig.minSdkVersion
        multiDexEnabled = AndroidConfig.multiDexEnabled
        vectorDrawables.useSupportLibrary = true

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf("room.schemaLocation" to "$projectDir/schemas".toString())
            }
        }
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

    lint {
        abortOnError = false
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))

    /*处理图片方向*/
    implementation(AndroidOfficial.exifinterface)

    api(AndroidOfficial.roomRuntime)
    api(AndroidOfficial.roomKtx)
    kapt(AndroidOfficial.roomCompiler)

    /*flexbox*/
    api(ThirdParty.flexboxLayout)

    /*内存泄漏检测 leakcanary*/
    debugImplementation(ThirdParty.leakcanaryAndroid)

    api(project(":base"))
    api(project(":mmkv"))
}
