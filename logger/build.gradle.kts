plugins {
    alias(libs.plugins.xeonyu.library)
}

android {
    namespace = "com.yzq.logger"
}

dependencies {
    implementation(libs.androidx.collection.ktx)
    implementation(libs.utilcodex)
}