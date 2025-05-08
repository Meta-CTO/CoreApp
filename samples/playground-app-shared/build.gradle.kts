import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.firebase.crashlytics)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.value(JvmTarget.JVM_17)
            freeCompilerArgs.addAll("-P", "plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=com.metacto.core.CommonParcelize")
        }
        compilations.all {
            kotlinOptions {
                jvmTarget = libs.versions.jvm.get()
            }
        }
    }

    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework(PlaygroundConfigs.FRAMEWORK_NAME) {
            baseName = PlaygroundConfigs.FRAMEWORK_NAME
            xcf.add(this)
            isStatic = true
        }
    }

    metadata {
        compilations.matching { it.name == "iosMain" }.all {
            compileTaskProvider.configure { enabled = false }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":ui:core-ui"))
        }

        androidMain.dependencies {
            // ANDROID DEPENDENCIES
        }

        iosMain.dependencies {
            // IOS DEPENDENCIES
        }
    }
}

android {
    namespace = PlaygroundConfigs.NAMESPACE
    compileSdk = libs.versions.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
    sourceSets["main"].res.srcDirs("src/androidMain/res", "src/commonMain/resources")

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        versionCode = PlaygroundConfigs.VERSION_CODE
        versionName = PlaygroundConfigs.VERSION_NAME

        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"), "./proguard-rules.pro"
        )
    }

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.jvm.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.jvm.get())
    }

    signingConfigs {
        Buildable.all().forEach {
            maybeCreate(it.name).apply {
                storeFile = file(it.storeFile)
                storePassword = it.storePassword
                keyAlias = it.keyAlias
                keyPassword = it.keyPassword
            }
        }
    }

    buildTypes {
        Buildable.all().forEach {
            maybeCreate(it.name).apply {
                isDebuggable = it.isDebuggable
                isMinifyEnabled = it.isMinifyEnabled
                isShrinkResources = it.isShrinkResources
                signingConfig = signingConfigs.getByName(it.name)
            }
        }
    }

    flavorDimensions("default")
    productFlavors {
        PlaygroundProductFlavor.all().forEach { flavor ->
            maybeCreate(flavor.toString()).apply {
                applicationId = flavor.applicationId
                versionNameSuffix = flavor.versionNameSuffix
                resValue(
                    type = "string",
                    name = "app_name",
                    value = flavor.appName
                )
            }
        }
    }

    lint {
        abortOnError = false
        checkReleaseBuilds = false
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.android.get()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.jvm.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.jvm.get())
    }

    kotlin {
        jvmToolchain(libs.versions.jvm.get().toInt())
    }
}

compose.resources {
    publicResClass = false
    packageOfResClass = "${PlaygroundConfigs.NAMESPACE}.resources"
    generateResClass = always
}
