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
//        mavenLocal()
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
//            from("com.xeonyu:version-catalog:0.1.1")
            /*如果名字为libs.versions，这里就不需要手动写，默认就会依赖*/
//            from(files("gradle/libs.versions_backup.toml"))
        }
//        create("gaodeLibs") {
//            library("location", "com.amap.api:location:6.3.0")
//        }
    }

}


/*settings.gradle里的代码会在初始化回调之前执行*/
println("settings.gradle 执行")

println("rootProjectName:${rootProject.name}")

gradle.addBuildListener(object : BuildAdapter() {

    override fun settingsEvaluated(settings: Settings) {
        super.settingsEvaluated(settings)
        "初始化阶段结束".prependIndent().let(::println)
    }

    override fun projectsLoaded(gradle: Gradle) {
        super.projectsLoaded(gradle)
        println("projectsLoaded")
    }

    override fun projectsEvaluated(gradle: Gradle) {
        super.projectsEvaluated(gradle)
        println("projectsEvaluated")
    }

    override fun buildFinished(result: BuildResult) {
        super.buildFinished(result)
        "构建结束".prependIndent().let(::println)
    }
}
)


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
include(":baidu")
include(":storage")

include(":react-native")
