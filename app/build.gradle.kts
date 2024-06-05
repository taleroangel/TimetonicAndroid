import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.compose)
    alias(libs.plugins.jetbrains.kotlin.serialization)

    alias(libs.plugins.google.ksp)
    alias(libs.plugins.google.dagger.hilt)
}

android {
    namespace = "dev.taleroangel.timetonic"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.taleroangel.timetonic"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_1_8)
    }
}

dependencies {

    // Kotlinx
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines)

    // Android Base
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))

    // Jetpack Compose and Material 3
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.ui.material3)
    implementation(libs.androidx.material.icons)
    implementation(libs.android.material)

    // Hilt
    implementation(libs.google.dagger.hilt.android)
    ksp(libs.google.dagger.hilt.compiler)

    // Navigation
    implementation(libs.androidx.navigation.ktx)
    implementation(libs.androidx.navigation.compose)

    // OkHttp
    implementation(platform(libs.squareup.okhttp.bom))
    implementation(libs.squareup.okhttp)

    // Arrow
    implementation(platform(libs.arrow.bom))
    implementation(libs.arrow.core)
    implementation(libs.arrow.fx.coroutines)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    androidTestImplementation(libs.androidx.navigation.testing)
}