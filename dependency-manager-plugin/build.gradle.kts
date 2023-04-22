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

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")
}

gradlePlugin {
    plugins {
        create("version") {
            id = "com.yzq.dependency-manager"
            implementationClass = "com.yzq.dependency_manager_plugin.DependencyManager"
        }
    }
}
