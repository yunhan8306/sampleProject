plugins {
    alias(libs.plugins.sample.android.library)
    alias(libs.plugins.sample.android.presentation)
    alias(libs.plugins.sample.android.test)
}

android {
    namespace = "com.yunhan.presentation.base"
}

dependencies {

    implementation(libs.androidx.core.ktx)
}