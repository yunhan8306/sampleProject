plugins {
    alias(libs.plugins.sample.android.library.compose)
    alias(libs.plugins.sample.android.test)
}

android {
    namespace = "com.yunhan.presentation.designsystem"
}

dependencies {

    implementation(libs.androidx.core.ktx)
}