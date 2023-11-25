@Suppress("DSL_SCOPE_VIOLATION")
plugins {
//    id("com.yzq.android.application")
//    alias(libs.plugins.android.application)
//    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.xeonyu.application)
    alias(libs.plugins.ksp)
    alias(libs.plugins.xeonyu.dependencyAnalysis)
    id("therouter")

}
println("getRootDir():${rootDir}")

//apply from: file("${getRootDir()}/gradle_script/bytex.gradle")

dependencyAnalysis {
    enable = true
    collectFileDetail = true
    logEnable = true
}

android {
    namespace = "com.yzq.kotlincommon"
    defaultConfig {
        applicationId = "com.yzq.kotlincommon"
        versionCode = 10001
        versionName = "1.0.1"
        minSdk = 23
        vectorDrawables {
            useSupportLibrary = true
        }
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
        buildConfig = true
    }


    lint {
        baseline = file("lint-baseline.xml")
    }


//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_17
//        targetCompatibility = JavaVersion.VERSION_17
//    }
//    kotlinOptions {
//        jvmTarget = JavaVersion.VERSION_17.toString()
//    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.viewpager2)
    implementation(libs.androidx.coordinatorlayout)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.recyclerview)


    implementation(libs.xeonyu.zxing)
    implementation(libs.lottie)
    implementation(libs.jsoup)

    implementation(libs.bannerViewPager)

    implementation(libs.xeonyu.bsdiff)

    implementation(libs.coil)
    implementation(libs.xeonyu.cordova.webcontainer)

    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.androidx.constraintlayout)

    implementation(libs.therouter)
    ksp(libs.therouter.apt)

    implementation(libs.androidx.exifinterface)

    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)


    implementation(project(":gao-de-map"))
    implementation(project(":common"))
    implementation(project(":baidu"))
    implementation(libs.xeonyu.logger)


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



