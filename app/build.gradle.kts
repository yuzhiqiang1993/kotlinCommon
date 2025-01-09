@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.xeonyu.application)
    alias(libs.plugins.ksp)
    alias(libs.plugins.xeonyu.dependencyManager)
    id("therouter")
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)


}
println("getRootDir():${rootDir}")

//apply from: file("${getRootDir()}/gradle_script/bytex.gradle")

dependencyManager {
    //依赖分析
    analysis {
        enable = false
        collectFileDetail = true
        collectArtifactFilePath = true
    }
    //依赖替换,可以用于替换指定依赖以及统一版本
    replace {
        this.enable = false
        this.replaceMap = mapOf(
//            "org.jetbrains.kotlin:kotlin-stdlib" to "${libs.kotlin.stdlib.jdk8.get()}",
        )
    }

    //代码查找
    searchCode {
        enable = false
        searchStrings = listOf(
            " Log.i"
        )
        libFilters = listOf("com.xeonyu")
    }
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
            abiFilters += listOf("arm64-v8a")
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
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro"
            )/*给buildConfig中添加变量*/
            buildConfigField("boolean", "LOG_DEBUG", "false")
            buildConfigField("String", "BASE_URL", "\"https://debug.xxx.xxx/\"")//字符串的值直接写的话需要加转义符
            buildConfigField("String", "CUSTOME_FIELD", "\"${rootProject.ext.get("debugValue")}\"")
        }

        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            buildConfigField("boolean", "LOG_DEBUG", "true")
            buildConfigField("String", "BASE_URL", "\"https://release.xxx.xxx/\"")//字符串的值直接写的话需要加转义符
            buildConfigField(
                "String", "CUSTOME_FIELD", "\"${rootProject.ext.get("releaseValue")}\""
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
        /*品牌*/
        create("xiaomi") {
            dimension = "CHANNEL"
            manifestPlaceholders["CHANNEL"] = "xiaomi"
            manifestPlaceholders["META_CHANNEL_VALUE"] = "xiaomi"
        }
        create("oppo") {
            dimension = "CHANNEL"
            manifestPlaceholders["CHANNEL"] = "oppo"
            manifestPlaceholders["META_CHANNEL_VALUE"] = "oppo"
        }

        /*环境*/
        create("uat") {
            dimension = "ENV"
            manifestPlaceholders["ENV"] = "uat"
        }
        create("prd") {
            dimension = "ENV"
            manifestPlaceholders["ENV"] = "prd"
        }

    }


    buildFeatures {
        dataBinding = true
        viewBinding = true
        buildConfig = true
        compose = true
    }

    //kotlin 2.0开始，使用插件的方式引入compose
//    composeOptions {
//        //kotlin和compose版本对应关系：https://developer.android.com/jetpack/androidx/releases/compose-kotlin?hl=zh-cn
//        kotlinCompilerExtensionVersion = "1.5.13"
//    }


    lint {
        baseline = file("lint-baseline.xml")
    }


    packaging {
        jniLibs.pickFirsts.apply {
            add("lib/arm64-v8a/libc++_shared.so")
            add("lib/armeabi-v7a/libc++_shared.so")
            add("lib/x86/libc++_shared.so")
            add("lib/x86_64/libc++_shared.so")
        }
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    kotlinOptions {
//        jvmTarget = "1.8"
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar", "*.aar"), "dir" to "libs")))

    implementation(platform(libs.kotlin.bom))
    implementation(libs.bundles.kotlin)

    implementation(libs.androidx.viewpager2)
    implementation(libs.androidx.coordinatorlayout)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.recyclerview)


    implementation(libs.xeonyu.zxing)
    implementation(libs.xeonyu.logger)
    implementation(libs.xeonyu.floatview)
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


    implementation(libs.androidx.activity)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    androidTestImplementation(platform(libs.androidx.compose.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    implementation(libs.androidx.exifinterface)

    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    implementation(libs.bugly.pro)


    implementation(project(":gao-de-map"))
    implementation(project(":common"))
    implementation(project(":baidu"))
    implementation(project(":react-native"))
    implementation(project(":login"))
    implementation(project(":player"))
    implementation(project(":aliemas"))


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



