plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
//    compileOnly(gradleApi())
//    compileOnly(localGroovy())
}

gradlePlugin {
    plugins {
        create("version") {
            id = "com.yzq.build-manager"
            implementationClass = "com.yzq.build_manager.BuildManager"
        }
    }
}

println("复合构建===")
