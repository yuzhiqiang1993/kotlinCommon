import com.yzq.build_manager.AndroidOfficial
import com.yzq.build_manager.ThirdParty

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
    implementation(AndroidOfficial.appcompat)
    implementation(AndroidOfficial.material)
    implementation(AndroidOfficial.constraintlayout)

    api(ThirdParty.materialDialogsCore)
    api(ThirdParty.materialDialogsInput)
    api(ThirdParty.materialDialogsBottomsheets)
    api(ThirdParty.materialDialogsLifecycle)

    api(ThirdParty.dateTimePicker)
}
