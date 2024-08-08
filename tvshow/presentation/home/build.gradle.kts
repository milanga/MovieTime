plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    kotlin("kapt")
}

android {
    namespace = "com.movietime.tvshow.home"
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
    // AndroidX
    implementation(libs.androidx.core.ktx)
    //UI
    implementation(libs.compose.ui)
    // Tooling support (Previews, etc.)
    implementation(libs.compose.ui.tooling)
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation(libs.compose.foundation)
    // Material Design
    implementation(libs.material3)
    // Integration with ViewModels
    implementation(libs.compose.viewmodel.lifecycle)
    // Lifecycle
    implementation(libs.lifecycle)
    //Navigation
    implementation(libs.navigation)
    //Accompanist
    implementation(libs.accompanist.placeholder)
    //DI
    implementation(libs.hilt)
    kapt(libs.dagger.hilt.compiler)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    implementation(project(":core:presentation"))
    implementation(project(":domain"))
    implementation(project(":core:views"))
    implementation(project(":tvshow:presentation:detail"))
}
