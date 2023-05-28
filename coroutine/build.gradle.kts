plugins {
    id("com.yzq.android.library")
}

android {
    namespace = "com.yzq.coroutine"


}

dependencies {
//    api(libs.kotlinx.coroutines.core)
    api(libs.kotlinx.coroutines.android)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.fragment.ktx)
}
