import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream
import java.io.FileOutputStream

plugins {
    id(Plugins.ANDROID_LIBRARY_PLUGIN)
    kotlin(Plugins.MULTIPLATFORM_PLUGIN)
    id(Plugins.MAVEN_PUBLISH)
    id(Plugins.SIGNING)
}

val versionProperties = Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, Constants.VERSIONS_PROPERTIES)))
}

val currentVersion = versionProperties.getProperty(Constants.PUBLISH_VERSION) as String
val libName = "mediaPlayers"

version = currentVersion
group = Constants.GROUP_ID

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
            // ANY COMMON MAIN DEPENDENCIES
            // TODO: remove this dependency and use the one from new commonDependencies module
            // TODO: this to hide errors only
            implementation(project(":coreapp"))
        }

        androidMain.dependencies {
            // ANDROID ANDROID DEPENDENCIES
        }

        iosMain.dependencies {
            // IOS DEPENDENCIES
        }
    }

    task("testClasses")
}

android {
    namespace = "com.metacto.kmm.mediaplayers"
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

publishing {
    repositories {
        val localProperties = gradleLocalProperties(rootDir, providers)
        var publishUserRepo = localProperties.getProperty(Constants.PUBLISH_REPO_USER)
        var publishTokenRepo = localProperties.getProperty(Constants.PUBLISH_REPO_TOKEN)

        if (publishUserRepo.isNullOrEmpty()) {
            publishUserRepo = ""
            localProperties.setProperty(Constants.PUBLISH_REPO_USER, publishUserRepo)
        }

        if (publishTokenRepo.isNullOrEmpty()) {
            publishTokenRepo = ""
            localProperties.setProperty(Constants.PUBLISH_REPO_TOKEN, publishTokenRepo)
        }

        if (publishUserRepo.isEmpty() || publishTokenRepo.isEmpty()) {
            localProperties.store(
                FileOutputStream(File(rootDir, Constants.LOCAL_PROPERTIES)), null
            )
        }

        repositories {
            maven(Constants.MAVEN_URL) {
                name = Constants.PUBLISH_MAVEN_REPO_NAME
                credentials {
                    username = publishUserRepo
                    password = publishTokenRepo
                }
            }
        }
    }
}

