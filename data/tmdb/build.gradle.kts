plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
}

android {
    namespace = "com.movietime.data.tmdb"
    compileSdk = libs.versions.compileSdkVersion.get().toInt()

    defaultConfig {
        buildConfigField("String", "TMDB_API_KEY", project.properties["TMDB_API_KEY"] as String)
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
    //Networking
    //  Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.jackson)
    //  Okhttp
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    //Androidx
    implementation(libs.androidx.core.ktx)

    //DI
    implementation(libs.hilt)
    kapt(libs.dagger.hilt.compiler)

    implementation(project(":domain"))
    
}