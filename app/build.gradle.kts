plugins {
    alias(libs.plugins.sample.android.application)
    alias(libs.plugins.sample.android.application.compose)
    alias(libs.plugins.sample.hilt)
}

android {
    namespace = "com.yunhan.sample"
    compileSdk = 35

    packagingOptions {
        resources {
            excludes += "META-INF/versions/9/OSGI-INF/MANIFEST.MF"
        }
    }

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

    implementation(projects.presentation.navigation)
    implementation(projects.di)

    // Core AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

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
}