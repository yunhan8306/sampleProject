plugins {
    alias(libs.plugins.sample.android.test)
    alias(libs.plugins.sample.hilt)
}

android {
    namespace = "com.yunhan.data.sample"
}

dependencies {

    implementation(projects.domain.sample)

    implementation(libs.androidx.core.ktx)
}