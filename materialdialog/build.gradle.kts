
plugins {
    id("com.yzq.android.library")
}

android {
    namespace = "com.yzq.materialdialog"
    buildTypes {
        release {
            consumerProguardFiles("proguard-rules.pro")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.androidx.constraintlayout)

    api(libs.materialDialog.core)
    api(libs.materialDialog.input)
    api(libs.materialDialog.bottomsheets)
    api(libs.materialDialog.lifecycle)

    api(libs.dateTimePicker)
}
