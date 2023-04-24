plugins {
    id("com.yzq.android.application")
    id("com.yzq.android.room")
    id("com.yzq.theRouter")

}
println("getRootDir():${rootDir}")

//apply from: file("${getRootDir()}/gradle_script/bytex.gradle")

android {
    namespace = "com.yzq.kotlincommon"
    defaultConfig {
        applicationId = "com.yzq.kotlincommon"
        versionCode = 10001
        versionName = "1.0.1"
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
    }


    lint {
        baseline = file("lint-baseline.xml")
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.viewpager2)
    implementation(libs.androidx.coordinatorlayout)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.recyclerview)


    implementation(libs.zxingYzq)
    implementation(libs.jsoup)

    implementation(libs.bannerViewPager)

    implementation(libs.xeonBsDiff)

    implementation(libs.coil)

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



