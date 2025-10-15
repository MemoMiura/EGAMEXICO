import java.util.Properties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin") // solo si usas SafeArgs en este módulo
    id("kotlin-parcelize")
}

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        file.inputStream().use { load(it) }
    }
}

val debugMiuraboxBaseUrl = localProperties.getProperty("miurabox.baseUrl.debug")
    ?: localProperties.getProperty("miurabox.baseUrl")
    ?: "http://127.0.0.1:8000/"

val releaseMiuraboxBaseUrl = localProperties.getProperty("miurabox.baseUrl.release")
    ?: localProperties.getProperty("miurabox.baseUrl")
    ?: "https://users-api.miurabox.com/"

android {
    namespace = "com.cursosant.insurance"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildTypes {
        getByName("debug") {
            buildConfigField("String", "MIURABOX_BASE_URL", "\"$debugMiuraboxBaseUrl\"")
        }
        getByName("release") {
            buildConfigField("String", "MIURABOX_BASE_URL", "\"$releaseMiuraboxBaseUrl\"")
        }
    }
}

dependencies {
    val coreKtxVersion = "1.12.0"
    val appcompatVersion = "1.6.1"
    val materialVersion = "1.8.0"
    val hiltVersion = "2.47"
    val navigationVersion = "2.6.0"
    val lifecycleVersion = "2.6.2"
    val pagingVersion = "3.2.1"

    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.9.24"))
    implementation("androidx.core:core-ktx:$coreKtxVersion")
    implementation("androidx.appcompat:appcompat:$appcompatVersion")
    implementation("com.google.android.material:material:$materialVersion")

    // Hilt
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    kapt("androidx.hilt:hilt-compiler:1.1.0")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")

    // Paging
    implementation("androidx.paging:paging-runtime-ktx:$pagingVersion")

    // Retrofit + OkHttp
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    // Firebase Messaging (FCM)
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-messaging-ktx")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Tests
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.15.1")
    kapt("com.github.bumptech.glide:compiler:4.15.1")

     // Retrofit Scalars
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")

    // SplashScreen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // PDF Viewer

    implementation("com.github.chrisbanes:PhotoView:2.3.0")



    // javax.inject
    implementation("javax.inject:javax.inject:1")
}


kapt {
    correctErrorTypes = true
}
