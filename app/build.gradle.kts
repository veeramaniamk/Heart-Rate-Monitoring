plugins {
    id("com.android.application")
}

android {
    namespace = "com.saveetha.heartrate"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.saveetha.heartrate"
        minSdk = 26
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
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("org.apache.commons:commons-math3:3.6.1")
    implementation("com.squareup.retrofit2:retrofit:2.3.0")
    implementation("com.squareup.retrofit2:converter-gson:2.3.0")
    implementation("com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:3.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:3.9.0")
    implementation("com.jjoe64:graphview:4.2.2")
    //noinspection GradleCompatible
    implementation("com.android.support:support-compat:27.1.1") {
        exclude(group = "androidx.core", module = "core")
    }
    implementation("com.google.android.play:app-update:2.1.0")
    //for animation
   // implementation ("androidx.navigation: navigation-fragment-ktx: 2.2.1")
    //implementation ("androidx.navigation: navigation-ui-ktx: 2.2.1");
}