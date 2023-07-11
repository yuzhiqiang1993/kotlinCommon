/*原本的buildscript 插件管理*/
pluginManagement {
    println("pluginManagement")

    repositories {
        mavenLocal()
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.aliyun.com/repository/public/")
        }
        maven {
            url = uri("https://maven.aliyun.com/nexus/content/repositories/releases/")
        }
        maven {
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        }

        maven {
            url = uri("https://artifact.bytedance.com/repository/byteX/")
        }
        gradlePluginPortal()

    }
}


/*原本的allprojects 依赖管理*/
dependencyResolutionManagement {
    println("dependencyResolutionManagement")
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        }
        mavenLocal()
        google()
        mavenCentral()

        maven {
            url = uri("https://maven.aliyun.com/nexus/content/repositories/releases/")
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/public/")
        }
        maven {
            url = uri("https://jitpack.io")
        }
    }

    versionCatalogs {
        create("libs") {
            from("com.xeonyu:version-catalog:0.0.9")
//            from(files("gradle/libs.versions_backup.toml"))
        }
//        create("gaodeLibs") {
//            library("location", "com.amap.api:location:6.3.0")
//        }
    }

}


/**
 *
 * gradle 中两个重要的概念
 * 1.project:表示一个项目，例如app module,其他子module。
 * 2.task:表示一个具体要执行的任务 例如：assembleRelease
 *
 *
 * gradle 生命周期（三个阶段）
 * 1. 初始化阶段（settingsEvaluated）  执行的就是setting.gradle中的代码，主要是看include了哪些模块，生成任务依赖图
 * 2. 配置阶段（projectsLoaded，projectsEvaluated）    执行的各个模块中的build.gradle中的代码
 * 3. 构建阶段（buildFinished）    把配置阶段中生成好的任务依赖图执行，主要是执行task
 */

/**
 * settings.gradle 运行在初始化阶段
 */

/*settings.gradle里的代码会在初始化回调之前执行*/
println("settings.gradle执行")

println("rootProjectName:${rootProject.name}")

/*gradle的生命周期回调*/
//gradle.addBuildListener(object : BuildAdapter() {
//    override fun beforeSettings(settings: Settings) {
//        super.beforeSettings(settings)
//        project("初始化阶段：$settings")
//    }
//
//    override fun settingsEvaluated(settings: Settings) {
//        super.settingsEvaluated(settings)
//        println("初始化阶段完成")
//    }
//
//    override fun projectsLoaded(gradle: Gradle) {
//        super.projectsLoaded(gradle)
//        println("projectsLoaded")
//    }
//
//    override fun projectsEvaluated(gradle: Gradle) {
//        super.projectsEvaluated(gradle)
//        println("配置阶段完成")
//
//        rootProject.children.forEach {
//            println("子工程名称是：${it.name},；路径是：${it.projectDir}")
//        }
//    }
//})

/*用来声明当前工程都包含哪些子工程*/
include(":app")
include(":common")
include(":gao-de-map")
include(":base")
include(":img")
include(":materialdialog")
include(":widget")
include(":permission")
include(":aliemas")
include(":network-status")
//include(":application")
include(":coroutine")
include(":binding")
include(":mmkv")
include(":statusbar")
include(":baidu")
include(":storage")
//include(":logger")
