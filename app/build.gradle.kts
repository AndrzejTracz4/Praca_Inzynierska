plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid) version "1.9.21"
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    kotlin("plugin.serialization") version "1.9.21"
}

android {
    namespace = "com.example.pracainynierska"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.pracainynierska"
        minSdk = 24
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
                "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1,LICENSE.md,LICENSE.txt,LICENSE-notice.md,NOTICE.md,NOTICE.txt}"
        }
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-compiler:2.44")

    // Compose BOM
    implementation(platform("androidx.compose:compose-bom:2024.08.00"))

    // Compose libraries
    implementation("androidx.core:core")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.runtime:runtime-livedata")
    implementation("androidx.compose.foundation:foundation")

    // Other dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.6")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("com.google.accompanist:accompanist-flowlayout:0.24.13-rc")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Lottie
    implementation("com.airbnb.android:lottie-compose:6.0.0")

    // API
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.21")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.21")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.1")
    implementation("com.squareup.moshi:moshi-adapters:1.15.1")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("io.kotlintest:kotlintest-runner-junit5:3.4.2")
    implementation("io.kotlintest:kotlintest-assertions:3.4.2")

    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    // Yaml
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.15.0")
}
