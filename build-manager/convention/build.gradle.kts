plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {

//    compileOnly("com.android.tools.build:gradle:${libs.versions.androidGradlePlugin}")
//    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin}")
//    compileOnly("com.android.tools.build:gradle:8.0.0")
//    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
    /*相当于上面的两行写法*/
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {

        register("androidApplication") {
            id = "com.yzq.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "com.yzq.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("theRouter") {
            id = "com.yzq.theRouter"
            implementationClass = "TheRouterConventionPlugin"
        }

        register("androidRoom") {
            id = "com.yzq.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
    }
}

println("复合构建===")
