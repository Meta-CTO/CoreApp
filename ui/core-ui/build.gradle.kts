import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream
import java.io.FileOutputStream

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.signing)
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
        compilerOptions {
            freeCompilerArgs.addAll("-P", "plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=com.metacto.core.CommonParcelize")
        }
        compilations.all {
            kotlinOptions {
                jvmTarget = libs.versions.jvm.get()
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
            // Core
            api(project(":core:core"))

            // Compose
            api(libs.compose.runtime)
            api(libs.compose.foundation)
            implementation(libs.compose.material)
            api(libs.compose.material3)
            api(libs.compose.animation)
            api(libs.compose.animation.graphics)
            api(libs.compose.material.icons.extended)
            api(libs.compose.components.resources)

            // Voyager
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.bottomsheet)
            api(libs.voyager.screenmodel)

            // Coil
            implementation(libs.coil)
            implementation(libs.coil.compose)
            implementation(libs.coil.network)
            implementation(libs.coil.svg)

            // Others
            implementation(libs.compottie)
            implementation(libs.shimmer)
        }

        androidMain.dependencies {
            // AndroidX
            api(libs.appcompat)
            api(libs.activity.compose)
            api(libs.core.ktx)
            api(libs.splashscreen)

            // Voyager
            implementation(libs.voyager.koin)
        }

        iosMain.dependencies {
            implementation(libs.compose.material)
        }
    }

    task("testClasses")
}

android {
    namespace = libNamespace
    compileSdk = libs.versions.compileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        consumerProguardFiles("consumer-rules.pro")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.jvm.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.jvm.get())
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