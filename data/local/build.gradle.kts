plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
}

android {
    namespace = "com.movietime.data.local"
    compileSdk = libs.versions.compileSdkVersion.get().toInt()

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    //DI
    implementation(libs.hilt)
    kapt(libs.dagger.hilt.compiler)

    //Datastore
    implementation(libs.datastore)
    implementation(libs.gson)

    implementation(project(":domain"))
}