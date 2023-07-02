import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType


/**
 * @description ThrRouter插件默认配置
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2023/4/23
 * @time    16:42
 */

class TheRouterConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            println("TheRouterConventionPlugin Apply")
            with(pluginManager) {

                if (this.hasPlugin("com.android.application")) {
                    println("是Application模块")
                    apply("therouter")
                }
                apply("com.google.devtools.ksp")

//                apply("org.jetbrains.kotlin.kapt")
            }

            println("${project.name}: configureKotlinAndroid")
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            val theRouter = libs.findLibrary("theRouter").get()
            val theRouterApt = libs.findLibrary("theRouterApt").get()

            println("theRouter:${theRouter.get().module}:${theRouter.get().version}")
            println("theRouterApt:${theRouterApt.get().module}:${theRouterApt.get().version}")

            dependencies {
                add("implementation", libs.findLibrary("theRouter").get())
//                add("kapt", libs.findLibrary("theRouterApt").get())
                add("ksp", libs.findLibrary("theRouterApt").get())
            }
        }
    }

}