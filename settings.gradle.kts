/*原本的buildscript 插件管理*/
pluginManagement {
    println("pluginManagement")

    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        google()
        maven {
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        }
        maven {
            url = uri("https://artifact.bytedance.com/repository/byteX/")
        }


    }


}


/*原本的allprojects 依赖管理*/
dependencyResolutionManagement {
    println("dependencyResolutionManagement")
    /**
     * FAIL_ON_PROJECT_REPOS： 如果设置此模式，任何直接在项目中声明的存储库都会触发构建错误。这对于确保在构建时不意外地使用项目中的存储库非常有用。（说白了就是不允许在项目中单独设置repositories）
     *
     * PREFER_PROJECT： 如果设置此模式，项目中声明的存储库将优先于设置中声明的存储库。这可以确保在存在相同的存储库时使用项目中的版本。
     *
     * PREFER_SETTINGS： 如果设置了此模式，将忽略项目中直接声明的任何存储库，而只使用通过构建脚本或插件声明的存储库。
     *
     *
     */
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    //强制刷新依赖

    repositories {
        mavenLocal()
        mavenCentral()
        google()
        maven {
            url = uri("https://jitpack.io")
        }
        maven {
            url = uri("https://maven.aliyun.com/nexus/content/repositories/releases/")
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/public/")
        }
        maven {
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        }
        /**
         * 下面可以用来统一处理仓库源
         * 例如给指定仓库添加excludeGroup
         */
        configureEach {
            when (this) {
                is MavenArtifactRepository -> {
                    println("${this.name}-${this.url}")
                }
            }
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
})


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
include(":dialog")
include(":widget")
include(":permission")
include(":aliemas")
include(":baidu")
include(":storage")

include(":react-native")
