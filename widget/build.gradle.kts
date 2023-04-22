import com.yzq.dependency_manager_plugin.AndroidConfig
import com.yzq.dependency_manager_plugin.AndroidOfficial
import com.yzq.dependency_manager_plugin.ThirdParty

plugins {
    id("com.android.library")
    kotlin("android")
}


android {
    namespace = "com.yzq.widget"
    compileSdk = AndroidConfig.compileSdkVersion

    defaultConfig {
        minSdk = AndroidConfig.minSdkVersion
        vectorDrawables.useSupportLibrary = true
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
        viewBinding = true
    }
    lint {
        abortOnError = false
    }


}


dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))


    implementation(AndroidOfficial.appcompat)
    implementation(AndroidOfficial.material)
    implementation(AndroidOfficial.vectordrawable)
    implementation(AndroidOfficial.constraintlayout)
    implementation(AndroidOfficial.annotationLib)
    implementation(AndroidOfficial.lifecycle)


    /*utils*/
    implementation(ThirdParty.utilcode)


    implementation(project(":coroutine"))


}

