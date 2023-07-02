dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        }
        //        mavenLocal()
    }
    versionCatalogs {
        create("libs") {
//            from(files("../gradle/libs.versions.toml"))
            from("com.xeonyu:version-catalog:0.0.1")
//            version("minSdk", "21")//这里可以覆盖catalog 中已有的版本号
//            version("theRouter", "1.1.4-rc6")//这里可以覆盖catalog 中已有的版本号

        }

        create("androidLibs") {
            version("minSdk", "21")//这里可以覆盖catalog 中已有的版本号
        }
    }
}

rootProject.name = "build-manager"
include(":convention")