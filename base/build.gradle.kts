import com.yzq.build_manager.AndroidConfig
import com.yzq.build_manager.AndroidOfficial
import com.yzq.build_manager.Retrofit
import com.yzq.build_manager.TheRouter
import com.yzq.build_manager.ThirdParty

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.yzq.base"
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

    api(AndroidOfficial.appcompat)
    api(AndroidOfficial.material)
    api(AndroidOfficial.vectordrawable)
    api(AndroidOfficial.constraintlayout)
    api(AndroidOfficial.annotationLib)
    api(AndroidOfficial.multiDex)

    /*ktx*/
    api(AndroidOfficial.corektx)
    api(AndroidOfficial.activityKtx)
    api(AndroidOfficial.fragmentKtx)
    api(AndroidOfficial.collectionKtx)

    api(AndroidOfficial.lifecycle)
    api(AndroidOfficial.liveData)
    api(AndroidOfficial.viewModel)
    api(AndroidOfficial.swiperefreshlayout)

    api(ThirdParty.utilcode)
    api(ThirdParty.moshiKotlin)

    api(TheRouter.theRouter)
    kapt(TheRouter.theRouterApt)

    /*retrofit*/
    api(Retrofit.okhttp)
    api(Retrofit.okhttpLogInterceptor)
    api(Retrofit.retrofit)
    api(Retrofit.moshiConverter)

    /*progressmanager*/
    api(ThirdParty.appStartFaster)
    api(ThirdParty.progressManager)
    api(ThirdParty.BRV)

    api(project(":application"))
    api(project(":network-status"))
    api(project(":img"))

    api(project(":materialdialog"))
    api(project(":permission"))
    api(project(":widget"))
    api(project(":aliemas"))
    api(project(":coroutine"))
    api(project(":binding"))
    api(project(":statusbar"))
}
