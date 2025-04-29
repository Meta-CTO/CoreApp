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
private val libName = "media-players"
private val libNamespace = "com.metacto.core.ui.mediaplayers"

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
            // Core UI
            implementation(project(Dependencies.Modules.CORE_UI))
        }

        androidMain.dependencies {
            // Exo Player
            implementation(Dependencies.ExoPlayer.PLAYER)
            implementation(Dependencies.ExoPlayer.HLS)
            implementation(Dependencies.ExoPlayer.UI)
            implementation(Dependencies.ExoPlayer.SESSION)
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
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
    sourceSets["main"].res.srcDirs("src/androidMain/res", "src/commonMain/resources")

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