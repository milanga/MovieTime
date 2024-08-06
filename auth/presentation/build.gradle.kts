plugins {
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.android.library)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.compose.compiler)
    kotlin("kapt")
}


android {
    namespace = "com.movietime.auth"
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

composeCompiler{
    enableStrongSkippingMode = true
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
    //DI
    implementation(libs.hilt)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    // Integration with ViewModels
    implementation(libs.compose.viewmodel.lifecycle)
    // Lifecycle
    implementation(libs.lifecycle)

    implementation(project(":domain"))
}