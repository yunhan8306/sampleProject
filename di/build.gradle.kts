plugins {
    alias(libs.plugins.sample.android.library)
    alias(libs.plugins.sample.android.test)
    alias(libs.plugins.sample.hilt)
}

android {
    namespace = "com.yunhan.di"
}

dependencies {
    implementation(projects.presentation.sample)
    implementation(projects.domain.sample)
    implementation(projects.data.sample)

    implementation(libs.androidx.core.ktx)
}