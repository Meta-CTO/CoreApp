@file:Suppress("OPT_IN_USAGE")

import dev.icerock.gradle.MRVisibility
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

plugins {
    kotlin(Plugins.MULTIPLATFORM_PLUGIN)
    kotlin(Plugins.COCOAPODS_PLUGIN)
    id(Plugins.ANDROID_LIBRARY_PLUGIN)
    id(Plugins.COMPOSE_PLUGIN) version Versions.COMPOSE
    id(Plugins.SERIALIZATION_PLUGIN)
    id(Plugins.PARCELIZE_PLUGIN)
    id(Plugins.MOKO_RESOURCES_PLUGIN)
}

kotlin {
    targetHierarchy.default()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = Versions.JVM.toString()
            }
        }
    }

    cocoapods {
        version = "1.0.0"
        summary = "Sample app shared module"
        homepage = "https://www.metacto.com/"
        ios.deploymentTarget = Configs.IOS_DEPLOYEMENT_TARGET
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = Configs.SAMPLE_APP_FRAMEWORK_NAME
            isStatic = true
            linkerOpts.add("-lsqlite3")

            if (System.getenv("XCODE_VERSION_MAJOR") == "1500") {
                linkerOpts += "-ld64"
            }
            export(Dependencies.KMP_NOTIFIER)
        }
        pod(
            name = Dependencies.Pods.FIREBASE_AUTH,
            linkOnly = true
        )
        pod(
            name = Dependencies.Pods.AMPLITUDE,
            linkOnly = true
        )
        pod(name = Dependencies.Pods.CleverTap.SDK) {
            moduleName = Dependencies.Pods.CleverTap.MODULE
            linkOnly = true
        }
        pod(
            name = Dependencies.Pods.GOOGLE_SIGN_IN,
            version = Versions.GOOGLE_SIGN_IN_POD,
            linkOnly = true
        )
        pod(
            name = Dependencies.Pods.FIREBASE_DYNAMIC_LINKS,
            linkOnly = true
        )
        pod(
            name = Dependencies.Pods.FIREBASE_CRASHLYTICS,
            linkOnly = true
        )
        pod(
            name = Dependencies.Pods.FIREBASE_REMOTE_CONFIG,
            linkOnly = true
        )
        pod(
            name = Dependencies.Pods.FIREBASE_MESSAGING,
            linkOnly = true
        )
        pod(
            name = Dependencies.Pods.AWSS3,
            linkOnly = true
        )
        pod(
            name = Dependencies.Pods.APPS_FLYER,
            linkOnly = true
        )

        xcodeConfigurationToNativeBuildType["Debug"] = NativeBuildType.RELEASE
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(Dependencies.Modules.CORE_APP))
            }
        }

        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by getting {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
            }
        }
    }
}

android {
    namespace = Configs.SAMPLE_APP_ID
    compileSdk = Configs.COMPILE_SDK_VERSION

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
    sourceSets["main"].res.srcDirs("src/androidMain/res", "src/commonMain/resources")

    defaultConfig {
        minSdk = Configs.MIN_SDK_VERSION
    }

    compileOptions {
        sourceCompatibility = Versions.JVM
        targetCompatibility = Versions.JVM
    }

    kotlin {
        jvmToolchain(Versions.JVM.toString().toInt())
    }
}

multiplatformResources {
    resourcesPackage.set(Configs.SAMPLE_APP_ID)
    resourcesVisibility.set(MRVisibility.Internal)
    iosBaseLocalizationRegion.set("en")
    iosMinimalDeploymentTarget.set("14.1")
}

tasks.named("syncPodComposeResourcesForIos") {
    dependsOn("generateMRiosSimulatorArm64Main")
}