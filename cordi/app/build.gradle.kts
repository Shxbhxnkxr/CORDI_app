plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.cordi"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.cordi"
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
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Health Connect dependencies
    implementation("androidx.health:health-connect-client:1.0.0-alpha10")
    implementation("androidx.health:health-connect-samples:1.0.0-alpha10")

    // Google Play services for Maps
    implementation("com.google.android.gms:play-services-maps:18.0.0")

    // Kotlin standard library
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.0")

    // DJL dependencies for ML
    implementation("ai.djl:api:0.18.0")
    implementation("ai.djl.tensorflow:tensorflow-engine:0.18.0")
    implementation("ai.djl.tensorflow:tensorflow-model-zoo:0.18.0")
    implementation("ai.djl.android:tensorflow-lite:0.18.0")
    implementation("org.tensorflow:tensorflow-lite:2.7.0")
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
}
