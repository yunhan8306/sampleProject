plugins {
    alias(libs.plugins.sample.android.library)
    alias(libs.plugins.sample.android.test)
    alias(libs.plugins.sample.hilt)
}

android {
    namespace = "com.yunhan.presentation.di"
}

dependencies {
//    implementation(projects.presentation.navigation)
    implementation(projects.presentation.detail)
//    implementation(projects.presentation.sample)

    implementation(projects.presentation.util)
    implementation(projects.presentation.impl)

    implementation(libs.androidx.core.ktx)
}