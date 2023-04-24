import com.yzq.build_manager.AndroidOfficial
import com.yzq.build_manager.ThirdParty

plugins {
    id("com.yzq.android.library")
}


android {
    namespace = "com.yzq.widget"
    buildTypes {
        release {
            consumerProguardFiles("proguard-rules.pro")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        viewBinding = true
    }
}


dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))


    implementation(AndroidOfficial.appcompat)
    implementation(AndroidOfficial.material)
    implementation(AndroidOfficial.vectordrawable)
    implementation(AndroidOfficial.constraintlayout)
    implementation(AndroidOfficial.annotationLib)
    implementation(AndroidOfficial.lifecycle)


    /*utils*/
    implementation(ThirdParty.utilcode)


    implementation(project(":coroutine"))


}

