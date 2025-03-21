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
    implementation(libs.androidx.datastore.preference)
    implementation(libs.androidx.datastore.preference.core)
}