package scripts

plugins { id("com.android.application") apply false }

object BuildTypes {
    const val DEBUG = "debug"
    const val RELEASE = "release"
}

object ProductFlavors {
    const val RED_FILE = "redFile"
    const val RED_CAMERA = "redCamera"
    const val GREEN_FILE = "greenFile"
    const val GREEN_CAMERA = "greenCamera"
}

private object FlavorDimensions {
    const val DEFAULT = "default"
}

object Default {
    const val BUILD_TYPE = BuildTypes.DEBUG
    const val BUILD_FLAVOR = ProductFlavors.RED_FILE

    val BUILD_VARIANT = "${BUILD_FLAVOR.capitalize()}${BUILD_TYPE.capitalize()}"
}
val APP_NAME_RED_FILE by extra { "@string/app_name_red_file"}
val APP_NAME_RED_CAMERA by extra { "@string/app_name_red_camera"}
val APP_NAME_GREEN_FILE by extra { "@string/app_name_green_file"}
val APP_NAME_GREEN_CAMERA by extra { "@string/app_name_green_camera"}

android {
    buildTypes {
        getByName(BuildTypes.DEBUG) {
            isMinifyEnabled = false
            applicationIdSuffix = ".${BuildTypes.DEBUG}"
            isDebuggable = true
        }
        getByName(BuildTypes.RELEASE) {
            isMinifyEnabled = false //TODO update to true when in playstore production
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        android.applicationVariants.all {
            val variant = this
            variant.outputs .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
                .forEach { output ->
                    val outputFileName = "scanme-calculator-${variant.flavorName}.apk"
                    output.outputFileName = outputFileName
                }
        }
    }

    flavorDimensions(FlavorDimensions.DEFAULT)
    productFlavors {
        create(ProductFlavors.RED_FILE) {
            dimension = FlavorDimensions.DEFAULT
            applicationIdSuffix = ".${ProductFlavors.RED_FILE}"
            versionNameSuffix = "-${ProductFlavors.RED_FILE}"
            manifestPlaceholders["APP_NAME"] = APP_NAME_RED_FILE
        }
        create(ProductFlavors.RED_CAMERA) {
            dimension = FlavorDimensions.DEFAULT
            applicationIdSuffix = ".${ProductFlavors.RED_CAMERA}"
            versionNameSuffix = "-${ProductFlavors.RED_CAMERA}"
            manifestPlaceholders["APP_NAME"] = APP_NAME_RED_CAMERA
        }
        create(ProductFlavors.GREEN_FILE) {
            dimension = FlavorDimensions.DEFAULT
            applicationIdSuffix = ".${ProductFlavors.GREEN_FILE}"
            versionNameSuffix = "-${ProductFlavors.GREEN_FILE}"
            manifestPlaceholders["APP_NAME"] = APP_NAME_GREEN_FILE
        }
        create(ProductFlavors.GREEN_CAMERA) {
            dimension = FlavorDimensions.DEFAULT
            applicationIdSuffix = ".${ProductFlavors.GREEN_CAMERA}"
            versionNameSuffix = "-${ProductFlavors.GREEN_CAMERA}"
            manifestPlaceholders["APP_NAME"] = APP_NAME_GREEN_CAMERA
        }
    }
}