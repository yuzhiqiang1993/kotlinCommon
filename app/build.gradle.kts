import com.yzq.dependency_manager_plugin.AndroidConfig
import com.yzq.dependency_manager_plugin.AndroidOfficial
import com.yzq.dependency_manager_plugin.Kotlinx
import com.yzq.dependency_manager_plugin.TheRouter
import com.yzq.dependency_manager_plugin.ThirdParty

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("therouter")
}
println("getRootDir():${rootDir}")

//apply from: file("${getRootDir()}/gradle_script/bytex.gradle")

android {
    namespace = "com.yzq.kotlincommon"
    compileSdk = AndroidConfig.compileSdkVersion
    defaultConfig {
        applicationId = "com.yzq.kotlincommon"
        minSdk = AndroidConfig.minSdkVersion
        targetSdk = AndroidConfig.targetSdkVersion
        versionCode = AndroidConfig.versionCode
        versionName = AndroidConfig.versionName
        multiDexEnabled = AndroidConfig.multiDexEnabled
        vectorDrawables.useSupportLibrary = true
        resourceConfigurations += listOf("zh")
        ndk {
            abiFilters += listOf("arm64-v8a", "armeabi-v7a")
        }
    }


    signingConfigs {

        create("release") {
            storePassword = "123456"
            keyAlias = "kotlinCommon"
            keyPassword = "123456"
            storeFile = file("kotlinCommon")
        }
    }


    /*buildTypes一般会多加，主要是用来配置混淆，压缩，是否可以debug*/
    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            /*给buildConfig中添加变量*/
            buildConfigField("boolean", "LOG_DEBUG", "false")
            buildConfigField("String", "BASE_URL", "\"https://debug.xxx.xxx/\"")//字符串的值直接写的话需要加转义符
            buildConfigField("String", "CUSTOME_FIELD", "\"${rootProject.ext.get("debugValue")}\"")
        }
        /*给测试打包用 混淆 压缩 签名都开启  但是可以debug*/
//        getByName("qa") {
//            signingConfig = signingConfigs.getByName("release")
//            isMinifyEnabled = true
//            isZipAlignEnabled = true
//            isShrinkResources = true
//            isDebuggable = true //该值决定了BuildConfig中的DEBUG值
//            proguardFiles(getDefaultProguardFile("proguard-android.txt"), file("proguard-rules.pro"))
//            buildConfigField("boolean", "LOG_DEBUG", "true")
//            buildConfigField("String", "BASE_URL", "\"https://release.xxx.xxx/\"")//字符串的值直接写的话需要加转义符
//            buildConfigField("String", "CUSTOME_FIELD", "\"${rootProject.ext.releaseValue}\"")
//        }


        /*生产环境用*/
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            buildConfigField("boolean", "LOG_DEBUG", "true")
            buildConfigField("String", "BASE_URL", "\"https://release.xxx.xxx/\"")//字符串的值直接写的话需要加转义符
            buildConfigField(
                "String",
                "CUSTOME_FIELD",
                "\"${rootProject.ext.get("releaseValue")}\""
            )
        }
    }

    /**
     * 维度
     * 这里可以根据不同维度来做一些区分，比如多渠道，品牌，环境等
     * 最好大写，生成的BuildConfig中会包含该值
     */
    flavorDimensions += listOf("CHANNEL", "ENV")
    productFlavors {
        create("uat") {
            dimension = "ENV"
            manifestPlaceholders["ENV"] = "uat"
        }
        create("prd") {
            dimension = "ENV"
            manifestPlaceholders["ENV"] = "prd"
        }
        create("wandoujia") {
            dimension = "CHANNEL"
            manifestPlaceholders["CHANNEL"] = "wandoujia"
            manifestPlaceholders["META_CHANNEL_VALUE"] = "wandoujia"
        }
        create("yingyongbao") {
            dimension = "CHANNEL"
            manifestPlaceholders["CHANNEL"] = "yingyongbao"
            manifestPlaceholders["META_CHANNEL_VALUE"] = "yingyongbao"
        }
    }


    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    lint {
        baseline = file("lint-baseline.xml")
    }

    println("我是App工程，${project.name},我的路径是:${project.projectDir}")
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(Kotlinx.kotlinStdlib)
    implementation(AndroidOfficial.viewpager2)
    implementation(AndroidOfficial.coordinatorlayout)
    implementation(AndroidOfficial.splashscreen)
    implementation(AndroidOfficial.recyclerview)

    kapt(AndroidOfficial.roomCompiler)
    kapt(TheRouter.theRouterApt)

    implementation(ThirdParty.zxingYzq)
    implementation(ThirdParty.jsoup)

    implementation(ThirdParty.bannerViewPager)

    implementation(ThirdParty.xeonBsDiff)

    implementation(ThirdParty.coil)

    implementation(project(":gao-de-map"))
    implementation(project(":common"))
}


tasks.register("testTask") {
    doFirst {
        println("testTask执行 doFirst")
    }

    doLast {
        println("testTask执行 doLast")
    }
}

tasks.register("testTask1") {
    dependsOn("testTask")
    doLast {
        println("testTask1执行")
    }
}

/*project表示当前module的项目*/
println("我是App工程，${project.name},我的路径是:${project.projectDir}")



