plugins {
    alias(libs.plugins.sample.android.library)
    alias(libs.plugins.sample.android.test)
}

android {
    namespace = "com.yunhan.presentation.impl"
}

dependencies {
    implementation(projects.presentation.navigation)
    implementation(projects.presentation.detail)

    implementation(libs.androidx.core.ktx)
}