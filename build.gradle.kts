/**
 * 该处主要用来申明插件的版本，apply false表示不立即应用，而是自己手动应用
 */
println("根目录的build.gradle 开始执行")
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.theRouter) apply false
    alias(libs.plugins.benManesVersion) apply false

//    id("com.android.application") version "8.0.0" apply false
//    id("com.android.library") version "8.0.0" apply false
//    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
//    id("cn.therouter") version "1.1.4-beta1" apply false
//    id("com.github.ben-manes.versions") version "0.46.0" apply false
}

/**
 * 定义的一些变量，在其他脚本中可以通过 rootProject.ext.get("key") 获取
 */
ext {
    set("debugValue", "debugValue")
    set("releaseValue", "releaseValue")
}

print("根目录的build.gradle 开始执行")
tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}


/**
 * 检测不支持Jetifier的库
 * 通过运行./gradlew checkJetifierAll就可以打印出所有module的Jetifier使用情况
 */
//tasks.register("checkJetifierAll", group = "verification") {}

/*当前文件用来配置所有子工程公用的配置*/
println("我是根目录下的build.gradle")
apply(from = "gradle_script/checkVersions.gradle")


/*对子工程的公共配置*/
subprojects {
    /*依赖管理  基本上每个子项目都要依赖*/
//    apply plugin: 'com.yzq.dependency-manager'


    // 如果想应用到某个子项目中，可以通过 subproject.name 来判断应用在哪个子项目中
    // subproject.name 是你子项目的名字
    // 官方文档地址：https://guides.gradle.org/creating-multi-project-builds/#add_documentation
//    if (subproject.name == "app") {
//        apply plugin: 'com.android.application'
//    } else {
//        apply plugin: 'com.android.library'
//    }

//    tasks.whenTaskAdded {
//        if (name == "checkJetifier") {
//            tasks.getByName("checkJetifierAll").dependsOn(this)
//        }
//    }

//    this.tasks.whenTaskAdded { task ->
//        if (task.name == "checkJetifier") {
//            tasks.getByName("checkJetifierAll").dependsOn(task)
//        }
//    }
}


/**
 * 查看项目依赖关系
 * ./gradlew app:dependencies > deps.txt  --configuration wandoujiaPrdReleaseRuntimeClasspath
 */


/**
 * 检查依赖库是否有新版本
 * ./gradlew dependencyUpdates
 */


