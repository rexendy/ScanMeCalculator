object Kotlin {
    const val standardLibrary = "1.5.31"
    const val coroutines = "1.3.9"
}

object AndroidSdk {
    const val min = 23
    const val compile = 31
    const val target = compile
}

object AndroidClient {
    const val appId = "com.test.scanmecalculator"
    const val versionCode = 1
    const val versionName = "1.0.0"
    const val testRunner = "androidx.test.runner.AndroidJUnitRunner"
}

object BuildPlugins {

    object Versions {
        const val buildToolsVersion = "7.0.3"
        const val gradleVersion = "7.2"
        const val hiltVersion = "2.38.1"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.buildToolsVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.standardLibrary}"
    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltVersion}"

    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinKapt = "kotlin-kapt"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
    const val daggerHilt = "dagger.hilt.android.plugin"
}

object ScriptPlugins {
    const val infrastructure = "scripts.tasks"
    const val variants = "scripts.variants"
    const val compilation = "scripts.compilation"
}

object Libraries {
    private object Versions {
        const val appCompat = "1.4.1"
        const val constraintLayout = "2.1.3"
        const val material = "1.6.0"
        const val lifecycle = "2.2.0"
        const val lifecycleExtensions = "2.1.0"
        const val textRecognition = "18.0.0"
        const val hilt = "2.38.1"
    }

    const val material                 = "com.google.android.material:material:${Versions.material}"
    const val kotlinCoroutines         = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Kotlin.coroutines}"
    const val kotlinCoroutinesAndroid  = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Kotlin.coroutines}"
    const val appCompat                = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val constraintLayout         = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val lifecycleCompiler        = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
    const val viewModel                = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val liveData                 = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycleExtensions      = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExtensions}"
    const val mlKitTextRecognition     = "com.google.android.gms:play-services-mlkit-text-recognition:${Versions.textRecognition}"
    const val hiltCompiler             = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hiltDagger               = "com.google.dagger:hilt-android:${Versions.hilt}"
}

object TestLibraries {
    private object Versions {
        const val junit4 = "4.13.2"
        const val espressoCore = "3.4.0"
        const val testExtensions = "1.1.3"
    }

    const val junit4          = "junit:junit:${Versions.junit4}"
    const val espressoCore    = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val testExtJunit    = "androidx.test.ext:junit:${Versions.testExtensions}"
}