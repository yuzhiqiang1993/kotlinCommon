package com.yzq.dependency_manager_plugin


/**
 * @description 依赖管理 ./gradlew dependencyUpdates
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2023/1/13
 * @time    17:53
 */


object AndroidOfficial {
    const val material = "com.google.android.material:material:1.7.0"

    const val appcompat = "androidx.appcompat:appcompat:1.5.1"

    const val activityKtx = "androidx.activity:activity-ktx:1.6.1"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:1.5.5"
    const val collectionKtx = "androidx.collection:collection-ktx:1.2.0"
    private const val lifecycleKtxVersion = "2.5.1"

    // ViewModel
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleKtxVersion"

    // LiveData
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleKtxVersion"

    // Lifecycles only (without ViewModel or LiveData)
    const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleKtxVersion"

    const val vectordrawable =
        "androidx.vectordrawable:vectordrawable:1.1.0"
    const val constraintlayout =
        "androidx.constraintlayout:constraintlayout:2.1.4"
    const val annotationLib = "androidx.annotation:annotation:1.5.0"
    const val multiDex = "androidx.multidex:multidex:2.0.1"

    // KTX core
    const val corektx = "androidx.core:core-ktx:1.9.0"

    /*下拉刷新*/
    const val swiperefreshlayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

    /*recyclerview*/
    const val recyclerview = "androidx.recyclerview:recyclerview:1.2.1"

    const val viewpager2 = "androidx.viewpager2:viewpager2:1.1.0-beta01"

    private const val roomVersion = "2.5.0"
    const val roomRuntime = "androidx.room:room-runtime:$roomVersion"
    const val roomCompiler = "androidx.room:room-compiler:$roomVersion"
    const val roomKtx = "androidx.room:room-ktx:$roomVersion"

    const val exifinterface = "androidx.exifinterface:exifinterface:1.3.5"

    const val splashscreen = "androidx.core:core-splashscreen:1.0.0"

    const val coordinatorlayout = "androidx.coordinatorlayout:coordinatorlayout:1.2.0"
}

object Kotlinx {
    private const val kotlinxCoroutineVersion = "1.6.4"
    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutineVersion"
    const val coroutineAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlinxCoroutineVersion"

    private const val kotlinVersion = "1.7.20"

    /*一些官方库或者三方库依赖了 jdk7或者jdk8 所以最好在app中显示的依赖一下保证版本统一*/
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
    const val kotlinStdlibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    /*jdk8内部依赖了jdk7 jdk7内部又依赖了kotlin-stdlib 所以一般只需要依赖jdk8即可*/
//    const val kotlinStdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${kotlinVersion}"
}

object Retrofit {
    private const val okHttpVersion = "4.10.0"
    const val okhttp = "com.squareup.okhttp3:okhttp:$okHttpVersion"
    const val okhttpLogInterceptor = "com.squareup.okhttp3:logging-interceptor:$okHttpVersion"

    const val okhttpprofiler = "com.localebro:okhttpprofiler:1.0.8"

    private const val retrofitVersion = "2.9.0"
    const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    const val moshiConverter = "com.squareup.retrofit2:converter-moshi:$retrofitVersion"
}


object TheRouter {
    private const val theRouterVersion = "1.1.2"

    const val theRouter = "cn.therouter:router:${theRouterVersion}"
    const val theRouterApt = "cn.therouter:apt:${theRouterVersion}"
}

object ThirdParty {
    const val utilcode = "com.blankj:utilcodex:1.31.1"

    const val xxPermission = "com.github.getActivity:XXPermissions:16.6"

    const val buglyCrashreportUpgrade = "com.tencent.bugly:crashreport_upgrade:1.6.1"
    const val buglyNativeCrashreport = "com.tencent.bugly:nativecrashreport:3.9.2"

    const val mmkv = "com.tencent:mmkv:1.2.14"
    const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:1.14.0"

    const val progressManager =
        "com.github.JessYanCoding:ProgressManager:1.5.0"

    /*启动器*/
    const val appStartFaster = "com.github.aiceking:AppStartFaster:2.2.0"

    const val zxingYzq = "com.github.yuzhiqiang1993:zxing:2.2.9"

    const val bannerViewPager = "com.github.zhpanvip:BannerViewPager:3.5.8"

    const val jsoup = "org.jsoup:jsoup:1.15.3"

    const val xeonBsDiff = "io.github.yuzhiqiang1993:xeon_bsdiff:1.0.1"

    const val BRV = "com.github.liangjingkanji:BRV:1.3.88"

    const val flexboxLayout = "com.github.google:flexbox-layout:3.0.0"

    const val leakcanaryAndroid = "com.squareup.leakcanary:leakcanary-android:2.10"

    const val baseRecyclerViewAdapterHelper =
        "com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.7"

    const val eventBus = "org.greenrobot:eventbus:3.3.1"

    private const val glideVersion = "4.14.2"
    const val glide = "com.github.bumptech.glide:glide:$glideVersion"
    const val glideCompiler = "com.github.bumptech.glide:compiler:$glideVersion"

    /*photoView*/
    const val photoview = "com.github.chrisbanes:PhotoView:2.3.0"

    const val easyPhotos = "com.github.HuanTanSheng:EasyPhotos:3.1.5"

    private const val materialDialog = "3.3.0"
    const val materialDialogsCore = "com.afollestad.material-dialogs:core:$materialDialog"
    const val materialDialogsInput = "com.afollestad.material-dialogs:input:$materialDialog"
    const val materialDialogsBottomsheets =
        "com.afollestad.material-dialogs:bottomsheets:$materialDialog"
    const val materialDialogsLifecycle = "com.afollestad.material-dialogs:lifecycle:$materialDialog"

    const val dateTimePicker = "com.github.loperSeven:DateTimePicker:0.6.1"
}
