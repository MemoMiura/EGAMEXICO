plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("androidx.navigation.safeargs")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
}


android {
    namespace = "com.miurabox.ega.mexico"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.miurabox.ega.mexico"
        minSdk = 21
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    kotlin {
        jvmToolchain(17)
    }

    buildFeatures {
        dataBinding = true
    }
}


val splash_screen_version = "1.0.1"
val hilt_version = "2.51.1"
//val hilt_version = "2.51.1"
val nav_version = "2.6.0"

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.9.24"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(project(":XIvan:Library:Insurance"))


    //Splash Screen
    implementation ("androidx.core:core-splashscreen:$splash_screen_version")
    //Hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-compiler:2.51.1")
    //Navigation
    implementation ("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation ("androidx.navigation:navigation-ui-ktx:$nav_version")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

// Hilt: Allow references to generated code
kapt {
    correctErrorTypes = true
}