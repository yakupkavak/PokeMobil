plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id(libs.plugins.parcelize.withoutVersion.get().pluginId)
    id(libs.plugins.safeargs.withoutVersion.get().pluginId)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.pokemobil"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.pokemobil"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
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
}

dependencies {

    implementation(libs.facebook.shimmer)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.lottie)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.converter.gson)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.coil)
    implementation(libs.retrofit)
    implementation(libs.picasso)
    implementation(libs.glide)
    implementation(libs.gson)
    implementation(libs.moshi.kotlin)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
kapt {
    correctErrorTypes = true
}