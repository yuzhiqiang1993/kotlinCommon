package com.yzq.build_manager

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

/**
 * @description 默认的Android配置
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2023/4/23
 * @time    14:06
 */

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    commonExtension.apply {

        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
        val compileSdkVersion = libs.findVersion("compileSdk").get().requiredVersion.toInt()
        val minSdkVersion = libs.findVersion("minSdk").get().requiredVersion.toInt()

        println("compileSdkVersion:${compileSdkVersion}")
        println("minSdkVersion:${minSdkVersion}")


        compileSdk = compileSdkVersion

        defaultConfig {
            minSdk = minSdkVersion
//            vectorDrawables {
//                useSupportLibrary = true
//            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
//            isCoreLibraryDesugaringEnabled = true
        }

        kotlinOptions {
            // Treat all Kotlin warnings as errors (disabled by default)
            // Override by setting warningsAsErrors=true in your ~/.gradle/gradle.properties
//            val warningsAsErrors: String? by project
//            allWarningsAsErrors = warningsAsErrors.toBoolean()

            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=kotlin.RequiresOptIn",
                // Enable experimental coroutines APIs, including Flow
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=kotlinx.coroutines.FlowPreview",
                "-opt-in=kotlin.Experimental",
            )

            // Set JVM target to 17
            jvmTarget = JavaVersion.VERSION_17.toString()
        }

        lint {
            abortOnError = false
        }
    }

//    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {

//        add("coreLibraryDesugaring", libs.findLibrary("android.desugarJdkLibs").get())
    }
}

fun CommonExtension<*, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}
