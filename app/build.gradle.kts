plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.improveenglishvocabularyapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.improveenglishvocabularyapp"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true;
    }
}

dependencies {

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")

    implementation("com.squareup.moshi:moshi:1.15.1")
    implementation("com.squareup.retrofit2:converter-moshi:2.11.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.1")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.15.1")

    //Navigation
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.7")

    // okhttp3
    implementation("com.squareup.okhttp3:okhttp:4.9.0")

    //Room
    implementation("androidx.room:room-runtime:2.7.0-alpha04")
    implementation("androidx.room:room-ktx:2.7.0-alpha04")
    implementation("androidx.room:room-rxjava2:2.7.0-alpha04")
    ksp("androidx.room:room-compiler:2.7.0-alpha04")

    //Recycle View
    implementation ("androidx.recyclerview:recyclerview:1.3.2")

    //CardView
    implementation("androidx.cardview:cardview:1.0.0")

    //Glide
    implementation("com.github.bumptech.glide:glide:5.0.0-rc01")
    annotationProcessor("com.github.bumptech.glide:compiler:5.0.0-rc01")

    //Lottie
    implementation("com.airbnb.android:lottie:6.4.1")

    //Hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

    implementation("androidx.fragment:fragment:1.8.1")
    implementation("androidx.fragment:fragment-ktx:1.8.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.2")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}