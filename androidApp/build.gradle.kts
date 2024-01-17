plugins {
    kotlin(Plugins.MULTIPLATFORM_PLUGIN)
    id(Plugins.ANDROID_APPLICATION_PLUGIN)
    id(Plugins.COMPOSE_PLUGIN) version Versions.COMPOSE
    id(Plugins.PLAY_SERVICES_PLUGIN)
    id(Plugins.CRASHLYTICS_PLUGIN)
}

android {
    namespace = Configs.SAMPLE_APP_ID
    compileSdk = Configs.COMPILE_SDK_VERSION

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    packagingOptions.apply {
        setMerges(
            setOf(
                "MR/**",
                "META-INF/services/**",
                "font/**"
            )
        )
    }

    defaultConfig {
        applicationId = Configs.SAMPLE_APP_ID
        minSdk = Configs.MIN_SDK_VERSION
        targetSdk = Configs.TARGET_SDK_VERSION
        versionCode = Configs.MY_ATLAS_VERSION_CODE
        versionName = Configs.MY_ATLAS_VERSION_NAME

        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"), "./proguard-rules.pro"
        )
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
                applicationIdSuffix = flavor.applicationIdSuffix
                versionNameSuffix = flavor.versionNameSuffix
                resValue(
                    type = "string",
                    name = "app_name",
                    value = "SampleApp${flavor.appNameSuffix}"
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

kotlin {
    androidTarget()
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project(Dependencies.Modules.SAMPLE_APP_SHARED))
            }
        }
    }
}