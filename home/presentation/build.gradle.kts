plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    kotlin("kapt")
}

android {
    namespace = "com.movietime.home.presentation"
    compileSdk = libs.versions.compileSdkVersion.get().toInt()

    buildFeatures {
        compose = true
        // Disable unused AGP features
        aidl = false
        renderScript = false
        resValues = false
        shaders = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    //UI
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.util)
    // Tooling support (Previews, etc.)
    implementation(libs.compose.ui.tooling)
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation(libs.compose.foundation)
    // Material Design
    implementation(libs.material3)
    // Material design icons
    implementation(libs.material.icons)
    implementation(libs.material.icons.extended)
    //Navigation
    implementation(libs.navigation)

    implementation(project(":movie:presentation:home"))
    implementation(project(":tvshow:presentation:home"))
    implementation(project(":search:presentation:home"))
    implementation(project(":auth:presentation"))
}
