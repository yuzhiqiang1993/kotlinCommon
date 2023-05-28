import com.android.build.gradle.LibraryExtension
import com.yzq.build_manager.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * @description Android library的默认配置
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2023/4/23
 * @time    14:58
 */

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("AndroidLibraryConventionPlugin apply")
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig {
                    /*组件的混淆配置*/
                    consumerProguardFiles("consumer-rules.pro")
                }
                /*作为library 一般不指定targetSdk*/
//                defaultConfig.targetSdk = 33
            }

            dependencies {
            }
        }
    }
}