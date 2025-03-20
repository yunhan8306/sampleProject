plugins {
    alias(libs.plugins.sample.android.library)
    alias(libs.plugins.sample.android.test)
}

android {
    namespace = "com.yunhan.presentation.util"
}

dependencies {

    implementation(libs.androidx.core.ktx)
}