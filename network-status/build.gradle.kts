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
    implementation(libs.utilcodex)
    implementation(libs.androidx.annotation)

    //Lifecycles only (without ViewModel or LiveData)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(project(":application"))
    implementation(project(":permission"))
}
