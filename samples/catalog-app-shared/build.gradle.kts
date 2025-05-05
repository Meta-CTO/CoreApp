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
                jvmTarget = Versions.JVM.toString()
            }
        }
    }

    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework(CatalogAppConfigs.FRAMEWORK_NAME) {
            baseName = CatalogAppConfigs.FRAMEWORK_NAME
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
            implementation(project(Dependencies.Modules.CORE_UI))
            implementation(project(Dependencies.Modules.FILES))
            implementation(project(Dependencies.Modules.NOTIFICATIONS))
            implementation(project(Dependencies.Modules.PHONE_CORE))
            implementation(project(Dependencies.Modules.MEDIA_PLAYERS))
            implementation(project(Dependencies.Modules.CAMERA))
            implementation(project(Dependencies.Modules.YOUTUBE))
            implementation(project(Dependencies.Modules.PHONE_UI))
            implementation(project(Dependencies.Modules.IMAGE_PICKER))
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
    namespace = CatalogAppConfigs.NAMESPACE
    compileSdk = Configs.COMPILE_SDK_VERSION

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
    sourceSets["main"].res.srcDirs("src/androidMain/res", "src/commonMain/resources")

    defaultConfig {
        minSdk = Configs.MIN_SDK_VERSION
        versionCode = CatalogAppConfigs.VERSION_CODE
        versionName = CatalogAppConfigs.VERSION_NAME

        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"), "./proguard-rules.pro"
        )
    }

    compileOptions {
        sourceCompatibility = Versions.JVM
        targetCompatibility = Versions.JVM
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

    flavorDimensions(FlavorDimensions.DEFAULT)
    productFlavors {
        ProductFlavor.all().forEach { flavor ->
            maybeCreate(flavor.toString()).apply {
                applicationId = flavor.applicationId
                versionNameSuffix = flavor.versionNameSuffix
                resValue(
                    type = "string",
                    name = "app_name",
                    value = "CatalogApp${flavor.appNameSuffix}"
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
        kotlinCompilerExtensionVersion = Versions.COMPOSE_ANDROID
    }

    compileOptions {
        sourceCompatibility = Versions.JVM
        targetCompatibility = Versions.JVM
    }

    kotlin {
        jvmToolchain(Versions.JVM.toString().toInt())
    }
}

compose.resources {
    publicResClass = false
    packageOfResClass = "${CatalogAppConfigs.NAMESPACE}.resources"
    generateResClass = always
}
