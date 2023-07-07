@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.xeonyu.library)
}


android {
    namespace = "com.yzq.network_status"


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
