plugins {
    alias(libs.plugins.sample.android.application)
    alias(libs.plugins.sample.android.application.compose)
    alias(libs.plugins.sample.hilt)
}

android {
    namespace = "com.yunhan.sample"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.yunhan.sample"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Core AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.activity.compose)

    // Jetpack Compose
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.compose.foundation)
//    implementation(libs.androidx.compose.material3)
//    implementation(libs.androidx.compose.runtime)
//    implementation(libs.androidx.navigation.compose)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Image Loading (Coil)
    implementation(libs.coil.compose)

    // Networking (Retrofit & OkHttp)
    implementation(libs.retrofit.core)
    implementation(libs.okhttp.logging)

    // Testing
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.test.junit)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
//    debugImplementation(libs.androidx.compose.ui.tooling)
//    debugImplementation(libs.androidx.compose.ui.test.manifest)
}