import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream
import java.io.FileOutputStream

plugins {
    kotlin(Plugins.MULTIPLATFORM_PLUGIN)
    id(Plugins.ANDROID_LIBRARY_PLUGIN)
    id(Plugins.COMPOSE_PLUGIN) version Versions.COMPOSE
    id(Plugins.COMPOSE_COMPILER_PLUGIN) version Versions.KOTLIN
    id(Plugins.MAVEN_PUBLISH)
    id(Plugins.SIGNING)
}


private val versionProperties = Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, Configs.VERSIONS_PROPERTIES)))
}

private val currentVersion = versionProperties.getProperty(Configs.PUBLISH_VERSION) as String
private val libName = "core-ui"
private val libNamespace = "com.metacto.core.ui"

version = currentVersion
group = Configs.GROUP_ID

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = Versions.JVM.toString()
            }
        }
        publishLibraryVariants("debug", "release")
    }

    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework(libName) {
            baseName = libName
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
            // Common
            implementation(project(Dependencies.Modules.CORE))

            // Compose
            api(Dependencies.Compose.RUNTIME)
            api(Dependencies.Compose.FOUNDATION)
            api(Dependencies.Compose.MATERIAL)
            api(Dependencies.Compose.MATERIAL3)
            api(Dependencies.Compose.ANIMATION)
            api(Dependencies.Compose.ANIMATION_GRAPHICS)
            api(Dependencies.Compose.EXTENDED_ICONS)
            api(Dependencies.Compose.RESOURCES)

            // Coil
            implementation(Dependencies.Coil.CORE)
            implementation(Dependencies.Coil.COMPOSE)
            implementation(Dependencies.Coil.NETWORK)
            implementation(Dependencies.Coil.SVG)

            // Shimmer
            implementation(Dependencies.SHIMMER)
        }

        androidMain.dependencies {
            api(Dependencies.AndroidX.ACTIVITY_COMPOSE)
            api(Dependencies.AndroidX.APP_COMPAT)
        }

        iosMain.dependencies {
            // IOS DEPENDENCIES
        }
    }

    task("testClasses")
}

android {
    namespace = libNamespace
    compileSdk = Configs.COMPILE_SDK_VERSION
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = Configs.MIN_SDK_VERSION
        consumerProguardFiles("consumer-rules.pro")
    }
    compileOptions {
        sourceCompatibility = Versions.JVM
        targetCompatibility = Versions.JVM
    }
}

compose.resources {
    publicResClass = false
    packageOfResClass = "$libNamespace.resources"
    generateResClass = always
}

publishing {
    repositories {
        val localProperties = gradleLocalProperties(rootDir, providers)
        var publishUserRepo = localProperties.getProperty(Configs.PUBLISH_REPO_USER)
        var publishTokenRepo = localProperties.getProperty(Configs.PUBLISH_REPO_TOKEN)

        if (publishUserRepo.isNullOrEmpty()) {
            publishUserRepo = ""
            localProperties.setProperty(Configs.PUBLISH_REPO_USER, publishUserRepo)
        }

        if (publishTokenRepo.isNullOrEmpty()) {
            publishTokenRepo = ""
            localProperties.setProperty(Configs.PUBLISH_REPO_TOKEN, publishTokenRepo)
        }

        if (publishUserRepo.isEmpty() || publishTokenRepo.isEmpty()) {
            localProperties.store(
                FileOutputStream(File(rootDir, Configs.LOCAL_PROPERTIES)), null
            )
        }

        repositories {
            maven(Configs.MAVEN_URL) {
                name = Configs.PUBLISH_MAVEN_REPO_NAME
                credentials {
                    username = publishUserRepo
                    password = publishTokenRepo
                }
            }
        }
    }
}