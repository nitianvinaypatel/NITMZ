buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
    }
}


allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven(url = "https://jitpack.io")
    }
}



// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
}