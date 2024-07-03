plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.scareme"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.scareme"
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
                "proguard-rules.pro"
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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    //Navigation
    implementation (libs.androidx.navigation.compose)

    //Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    //Http3 interceptor
    implementation(libs.logging.interceptor)

    //Dagger - Hilt
    implementation (libs.hilt.android)
    implementation (libs.androidx.hilt.lifecycle.viewmodel)
    implementation (libs.androidx.hilt.navigation.compose)


    implementation (libs.androidx.material.icons.extended)

    //Cards
    implementation ("com.alexstyl.swipeablecard:swipeablecard:0.1.0")
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.26.1-alpha")

    //Coil - images
    implementation ("io.coil-kt:coil-compose:2.0.0")
    implementation ("io.coil-kt:coil:2.0.0")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}