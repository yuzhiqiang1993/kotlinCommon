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
        }
    }
}

rootProject.name = "build-manager"
include(":convention")