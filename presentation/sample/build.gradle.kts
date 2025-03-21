plugins {
    alias(libs.plugins.sample.android.library.compose)
    alias(libs.plugins.sample.android.presentation)
    alias(libs.plugins.sample.android.test)
}

android {
    namespace = "com.yunhan.presentation.sample"
}

dependencies {
    implementation(projects.presentation.detail)
    implementation(projects.domain.sample)

    implementation(libs.androidx.core.ktx)
}