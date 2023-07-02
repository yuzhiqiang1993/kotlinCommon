import com.android.build.api.dsl.ApplicationExtension
import com.yzq.build_manager.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.provideDelegate


/**
 * @description 默认的Application插件
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2023/4/23
 * @time    14:01
 */

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            val minSdk: Int by project
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            val targetSdkVersion = libs.findVersion("targetSdk").get().requiredVersion.toInt()
//            val versionCodeInt = libs.findVersion("versionCode").get().requiredVersion.toInt()
//            val versionNameStr = libs.findVersion("versionName").get().requiredVersion
            println("targetSdkVersion:${targetSdkVersion}")
//            println("versionCodeInt:${versionCodeInt}")
//            println("versionNameStr:${versionNameStr}")
            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig {
//                    versionCode = versionCodeInt
//                    versionName = versionNameStr
                    targetSdk = targetSdkVersion
                    multiDexEnabled = true
                }
            }
        }
    }

}