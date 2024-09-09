plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")

    id("kotlin-parcelize")
}

android {
    namespace = "com.ahk.tcpserver"
    compileSdk = 34

    defaultConfig {
        // applicationId = "com.ahk.tcpserver"
        minSdk = 27
        targetSdk = 34
        // versionCode = 1
        // versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    buildFeatures {
        aidl = true
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
    // rxjava
    implementation("io.reactivex.rxjava3:rxkotlin:3.0.1")

    // timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    implementation(project(":tcpaidl"))
}
