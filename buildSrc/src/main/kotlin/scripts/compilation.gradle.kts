package scripts

import org.gradle.kotlin.dsl.apply

plugins {
    id("com.android.application") apply false
    id("org.jetbrains.kotlin.android") apply false
}

android {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}