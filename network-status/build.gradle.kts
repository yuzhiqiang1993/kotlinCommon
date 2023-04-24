import com.yzq.build_manager.AndroidOfficial
import com.yzq.build_manager.ThirdParty

plugins {
    id("com.yzq.android.library")
}


android {
    namespace = "com.yzq.network_status"

    buildTypes {
        release {
            consumerProguardFiles("proguard-rules.pro")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}


dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    /*utils*/
    implementation(ThirdParty.utilcode)
    implementation(AndroidOfficial.annotationLib)

    //Lifecycles only (without ViewModel or LiveData)
    implementation(AndroidOfficial.lifecycle)
    implementation(project(":application"))
    implementation(project(":permission"))
}
