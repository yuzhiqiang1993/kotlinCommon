plugins {
    alias(libs.plugins.xeonyu.library)
}

android {
    namespace = "com.yzq.react_native"
    compileSdk = 34
}

dependencies {

    api(libs.react.android)
    api(libs.react.hermes.android)
    implementation(project(":base"))

}