import com.yzq.build_manager.AndroidOfficial
import com.yzq.build_manager.ThirdParty

plugins {
    id("com.yzq.android.library")
}

android {
    namespace = "com.yzq.binding"
    buildTypes {
        release {
            consumerProguardFiles("proguard-rules.pro")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(AndroidOfficial.appcompat)
    implementation(AndroidOfficial.corektx)
    implementation(AndroidOfficial.lifecycle)
    implementation(ThirdParty.utilcode)
}
