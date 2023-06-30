@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.yzq.android.library")
    id("com.yzq.android.room")
}

android {
    namespace = "com.yzq.storage"
}

dependencies {
    implementation(libs.utilcodex)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(project(":application"))
    api(project(":mmkv"))
    api(libs.threetenabp)

}