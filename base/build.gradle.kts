@Suppress("DSL_SCOPE_VIOLATION") plugins {
    alias(libs.plugins.xeonyu.library)
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

    //     https://mvnrepository.com/artifact/androidx.compose/compose-bom
    api(platform(libs.androidx.compose.compose.bom))
    api(libs.bundles.compose)

    debugApi(libs.androidx.compose.ui.tooling)
    debugApi(libs.androidx.compose.ui.tooling.preview)

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

    api(libs.xeonyu.coroutine)
    api(libs.xeonyu.logger)
    api(libs.xeonyu.application)
    api(libs.xeonyu.binding)
    api(libs.xeonyu.network.status)
    api(libs.toaster)
    //google 广告
    implementation(libs.play.services.ads.identifier)

//    api(libs.file.core)
//
//    api(libs.file.selector)
//    api(libs.file.compressor)

    api("com.github.javakam:file.core:3.9.8@aar")
    api("com.github.javakam:file.selector:3.9.8@aar")
    api("com.github.javakam:file.compressor:3.9.8@aar")

    api(project(":resource"))
    api(project(":img"))
    api(project(":dialog"))
    api(project(":permission"))
    api(project(":widget"))


}

tasks.register("printSpecificDependency") {
    doLast {
        val configuration = configurations.getByName("oppoPrdReleaseRuntimeClasspath")
        val dependency = configuration.incoming.resolutionResult.allDependencies.find {
            it.requested.displayName.contains("androidx.compose.material3:material3")
        }
        if (dependency != null) {
            println(dependency)
        } else {
            println("Dependency not found")
        }
    }
}


