plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.10"
    id("java-gradle-plugin")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())
}

gradlePlugin {
    plugins {
        create("version") {
            id = "com.yzq.dependency-manager"
            implementationClass = "com.yzq.dependency_manager_plugin.DependencyManager"
        }
    }
}

println("复合构建===")


ext {
    set("kotlin", "1.8.10")

}