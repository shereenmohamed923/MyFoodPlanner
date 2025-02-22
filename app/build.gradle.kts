plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.example.myfoodplanner"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myfoodplanner"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.legacy.support.v4)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    //glide
    implementation(libs.glide)
    //firebase
    implementation(libs.firebase.auth.v2310)
    implementation(libs.play.services.auth)
    implementation(libs.google.services)
    //navigation
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    //avatar
    implementation(libs.circleimageview)
    //RX
    implementation(libs.rxjava3.retrofit.adapter)
    implementation(libs.rxjava3.rxandroid)
    implementation (libs.rxjava3.rxjava)
    implementation(libs.room.rxjava3)
    //room
    implementation (libs.room.runtime)
    annotationProcessor (libs.room.compiler)
}