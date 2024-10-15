plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
    id("com.google.dagger.hilt.android")
    id ("androidx.navigation.safeargs")

}

android {
    namespace = "com.example.logisticcavan"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.logisticcavan"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
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
}

dependencies {
    implementation (libs.navigation.fragment)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    //hilt
    implementation(libs.hilt.android)
    annotationProcessor (libs.hilt.compiler)
    //rxjava
    implementation(libs.rxjava.android)

    //glide
    implementation(libs.glide)

    implementation ("com.google.code.gson:gson:2.8.8")

}