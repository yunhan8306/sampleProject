plugins {
    alias(libs.plugins.sample.android.library.compose)
    alias(libs.plugins.sample.android.presentation)
    alias(libs.plugins.sample.android.test)
}

android {
    namespace = "com.yunhan.presentation.detail"
}

dependencies {

    implementation(libs.androidx.core.ktx)
}