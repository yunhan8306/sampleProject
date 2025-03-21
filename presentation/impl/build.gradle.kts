plugins {
    alias(libs.plugins.sample.android.library)
    alias(libs.plugins.sample.android.test)
    alias(libs.plugins.sample.hilt)
}

android {
    namespace = "com.yunhan.presentation.impl"
}

dependencies {
    implementation(projects.presentation.detail)

    implementation(projects.presentation.util)

    implementation(libs.androidx.core.ktx)
}