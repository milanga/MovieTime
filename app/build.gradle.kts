plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.compose.compiler)
    kotlin("kapt")
}

android {
    compileSdk = libs.versions.compileSdkVersion.get().toInt()

    defaultConfig {
        applicationId = "com.milanga.movietime"
        minSdk = libs.versions.minSdkVersion.get().toInt()
        targetSdk = libs.versions.targetSdkVersion.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

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
    namespace = "com.movietime.main"
}

kapt {
    correctErrorTypes = true
}

composeCompiler {
    enableStrongSkippingMode = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // AndroidX
    implementation(libs.androidx.core.ktx)
    //UI
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.util)
    // Tooling support (Previews, etc.)
    implementation(libs.compose.ui.tooling)
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation(libs.compose.foundation)
    // Material Design
    implementation(libs.material3)
    // Integration with activities
    implementation(libs.compose.activity)
    // Integration with ViewModels
    implementation(libs.compose.viewmodel.lifecycle)
    // Lifecycle
    implementation(libs.lifecycle)
    //Navigation
    implementation(libs.navigation)
    //Accompanist
    implementation(libs.accompanist.navigation.animation)
    implementation(libs.accompanist.systemuicontroller)
    //DI
    implementation(libs.hilt)
    kapt(libs.dagger.hilt.compiler)

    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    implementation(project(":core:presentation"))
    implementation(project(":movie:presentation:home"))
    implementation(project(":domain"))
    implementation(project(":data:tmdb"))
    implementation(project(":data:trakt"))
    implementation(project(":data:local"))
    implementation(project(":data:memory"))
    implementation(project(":movie:presentation:detail"))
    implementation(project(":home:presentation"))
    implementation(project(":auth:presentation"))
}