@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.xeonyu.library)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.yzq.base"

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))

    api(libs.androidx.appcompat)
    api(libs.google.material)

    api(libs.androidx.vectordrawable)
    api(libs.androidx.constraintlayout)
    api(libs.androidx.annotation)
    api(libs.androidx.multidex)

    /*ktx*/
    api(libs.androidx.core.ktx)
    api(libs.androidx.activity.ktx)
    api(libs.androidx.fragment.ktx)
    api(libs.androidx.collection.ktx)

    api(libs.androidx.lifecycle.runtime.ktx)
    api(libs.androidx.lifecycle.livedata.ktx)
    api(libs.androidx.lifecycle.viewmodel.ktx)
    api(libs.androidx.swiperefreshlayout)

    api(libs.utilcodex)
    api(libs.moshiKotlin)


    /*retrofit*/
    api(libs.okhttp.core)
    api(libs.okhttp.logging.interceptor)
    api(libs.retrofit.core)
    api(libs.retrofit.converter.moshi)

    /*progressmanager*/
    api(libs.appStartFaster)
    api(libs.progressManager)
    api(libs.brv)

    api(libs.therouter)
    ksp(libs.therouter.apt)



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
    api(project(":logger"))


}
