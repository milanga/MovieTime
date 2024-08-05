// Top-level build file where you can add configuration options common to all sub-projects/modules.
apply(from = "versions.gradle")

//plugins {
//    id("com.android.application") version "8.5.1" apply false
//    id("org.jetbrains.kotlin.android") version "1.7.0" apply false
//    id("com.google.dagger.hilt.android") version "2.42" apply false
//}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.dagger.hilt) apply false
}

//subprojects {
//    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile).configureEach {
//        kotlinOptions {
//            // Treat all Kotlin warnings as errors (disabled by default)
//            allWarningsAsErrors = project.hasProperty("warningsAsErrors") ? project.warningsAsErrors : false
//
//            freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
//            // Enable experimental coroutines APIs, including Flow
//            freeCompilerArgs += "-Xopt-in=kotlin.Experimental"
//
//            // Set JVM target to 1.8
//            jvmTarget = "$kotlinJvmTarget"
//        }
//    }
//}
//
//task clean(type: Delete) {
//    delete rootProject.buildDir
//}