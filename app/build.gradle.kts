plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.nitmizoram.nitmz"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.nitmizoram.nitmz"
        minSdk = 21
        targetSdk = 34
        versionCode = 3
        versionName = "3.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.android.material:compose-theme-adapter:1.2.1")
    implementation ("com.etebarian:meow-bottom-navigation:1.2.0")
    implementation("androidx.navigation:navigation-fragment:2.7.6")
    implementation("androidx.navigation:navigation-ui:2.7.6")
    implementation("com.google.firebase:firebase-firestore:24.10.2")
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("com.google.firebase:firebase-messaging:23.4.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.airbnb.android:lottie:6.2.0")
    implementation ("com.github.smarteist:autoimageslider:1.2.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation("com.google.firebase:firebase-auth:22.3.1")
   implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation ("com.github.dhaval2404:imagepicker:2.1")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.github.denzcoskun:ImageSlideshow:0.1.2")
    implementation("com.firebaseui:firebase-ui-auth:8.0.2")
    implementation("com.firebaseui:firebase-ui-firestore:8.0.2")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("com.github.barteksc:android-pdf-viewer:2.8.2")
    implementation("com.google.android.play:app-update:2.1.0")

}