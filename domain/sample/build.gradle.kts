plugins {
    alias(libs.plugins.sample.android.test)
}

android {
    namespace = "com.yunhan.domain.sample"
}

dependencies {

    implementation(libs.androidx.core.ktx)
}