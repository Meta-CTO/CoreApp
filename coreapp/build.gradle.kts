@file:Suppress("OPT_IN_USAGE")

import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
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
    id(Plugins.SWIFT_KLIB)
    id(Plugins.MAVEN_PUBLISH)
    id(Plugins.SIGNING)
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

        publishAllLibraryVariants()
        publishLibraryVariantsGroupedByFlavor = true
    }

    cocoapods {
        version = "1.0.0"
        summary = "MetaCTO core app module"
        homepage = "https://metacto.com/"
        ios.deploymentTarget = Configs.IOS_DEPLOYEMENT_TARGET
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = Configs.CORE_APP_FRAMEWORK_NAME
            linkerOpts.add("-lsqlite3")

            if (System.getenv("XCODE_VERSION_MAJOR") == "1500") {
                linkerOpts += "-ld64"
            }
        }

        xcodeConfigurationToNativeBuildType["Debug"] = NativeBuildType.RELEASE
    }

    metadata {
        compilations.matching { it.name == "iosMain" }.all {
            compileTaskProvider.configure { enabled = false }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Compose
                api(Dependencies.Compose.RUNTIME)
                api(Dependencies.Compose.FOUNDATION)
                api(Dependencies.Compose.MATERIAL)
                api(Dependencies.Compose.MATERIAL3)
                api(Dependencies.Compose.ANIMATION)
                api(Dependencies.Compose.ANIMATION_GRAPHICS)
                api(Dependencies.Compose.EXTENDED_ICONS)
                api(Dependencies.Compose.RESOURCES)

                // Voyager
                api(Dependencies.Voyager.NAVIGATOR)
                api(Dependencies.Voyager.BOTTOM_SHEET)
                api(Dependencies.Voyager.TAB_NAVIGATOR)
                api(Dependencies.Voyager.SCREEN_MODEL)

                // Koin
                api(Dependencies.Koin.CORE)
                api(Dependencies.Koin.COMPOSE)

                // Moko
                api(Dependencies.Moko.RESOURCES)
                api(Dependencies.Moko.RESOURCES_COMPOSE)
                implementation(Dependencies.Moko.PARCELIZE)

                // Kotlin
                api(Dependencies.Kotlin.DATE_TIME)
                api(Dependencies.Kotlin.COLLECTIONS)

                // Coil
                api(Dependencies.Coil.CORE)
                api(Dependencies.Coil.COMPOSE)
                api(Dependencies.Coil.NETOWRK)

                // GitLive
                api(Dependencies.GitLive.CONFIG)

                // Others
                api(Dependencies.STRAPI_KMM)
                api(Dependencies.LIB_PHONE_NUMBER)
                api(Dependencies.COMPOTTIE)
                api(Dependencies.WEBVIEW)

            }
        }

        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                // Compose
                api(Dependencies.Compose.UI_TOOLING)

                // Android X
                api(Dependencies.AndroidX.ACTIVITY_COMPOSE)
                api(Dependencies.AndroidX.APP_COMPAT)
                api(Dependencies.AndroidX.CORE_KTS)
                api(Dependencies.AndroidX.SECURITY_CRYPTO)
                api(Dependencies.AndroidX.SPLASH_SCREEN)

                // Firebase
                api(platform(Dependencies.Firebase.BOM))
                api(Dependencies.Firebase.DYNAMIC_LINKS)
                api(Dependencies.Firebase.CRASHLYTICS)
                api(Dependencies.Firebase.ANALYTICS)
                api(Dependencies.Firebase.MESSAGING)
                api(Dependencies.Firebase.PLAY_SERVICES_AUTH)

                // Koin
                api(Dependencies.Koin.ANDROID)
                api(Dependencies.Koin.ANDROID_COMPOSE)

                // Voyager
                api(Dependencies.Voyager.KOIN)

                // Coil
                api(Dependencies.Coil.ANDROID_COMPOSE)
                api(Dependencies.Coil.ANDROID_GIF)

                // Exo Player
                api(Dependencies.ExoPlayer.PLAYER)
                api(Dependencies.ExoPlayer.HLS)
                api(Dependencies.ExoPlayer.UI)

                // Youtube Player
                api(Dependencies.YoutubePlayer.CORE)
                api(Dependencies.YoutubePlayer.CUSTOM_UI)

                // Others
                api(Dependencies.ANDROID_IMAGE_PICKER)
                api(Dependencies.ANDROID_CROPPER)
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
                api(Dependencies.Compose.MATERIAL)
            }
        }
    }
}

android {
    namespace = Configs.CORE_APP_ID
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
    resourcesPackage.set(Configs.CORE_APP_ID) // required
    resourcesVisibility.set(MRVisibility.Internal) // optional, default Public
    iosBaseLocalizationRegion.set("en") // optional, default "en"
    iosMinimalDeploymentTarget.set("11.0")
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

publishing {
    repositories {
        repositories {
            maven("https://maven.pkg.github.com/Meta-CTO/CoreApp") {
                name = "Github"
                credentials {
                    username = gradleLocalProperties(rootDir).getProperty("PUBLISH_REPO_USER") as String
                    password = gradleLocalProperties(rootDir).getProperty("PUBLISH_REPO_TOKEN") as String
                }
            }
        }
    }

    publications.withType<MavenPublication> {
        artifact(javadocJar)
        groupId = "com.metacto"
        version = gradleLocalProperties(rootDir).getProperty("PUBLISH_VERSION") as String

        pom {
            name.set("coreApp")
            description.set("Compose Multiplatform library for MetaCTO core app module")
            url.set("https://github.com/Meta-CTO/CoreApp")

            licenses {
                license {
                    name.set("MIT")
                    url.set("https://opensource.org/licenses/MIT")
                }
            }
            developers {
                developer {
                    id.set("ahmedsalemelzeiny")
                    name.set("Ahmed Salem Elzeiny")
                    email.set("ahmedsalemelzeiny2013@gmail.com")
                }
            }
            scm {
                url.set("https://github.com/Meta-CTO/CoreApp")
            }
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}

afterEvaluate {
    tasks.named("androidDebugSourcesJar") {
        dependsOn("generateMRandroidMain")
    }
    tasks.named("androidReleaseSourcesJar") {
        dependsOn("generateMRandroidMain")
    }
    tasks.named("iosX64SourcesJar") {
        dependsOn("generateMRandroidMain")
    }
    tasks.named("iosX64SourcesJar") {
        dependsOn("generateMRiosX64Main")
    }
    tasks.named("iosSimulatorArm64SourcesJar") {
        dependsOn("generateMRandroidMain")
    }
    tasks.named("iosSimulatorArm64SourcesJar") {
        dependsOn("generateMRiosSimulatorArm64Main")
    }
    tasks.named("iosArm64SourcesJar") {
        dependsOn("generateMRandroidMain")
    }
    tasks.named("iosArm64SourcesJar") {
        dependsOn("generateMRiosArm64Main")
    }
    tasks.named("sourcesJar") {
        dependsOn("generateMRandroidMain")
    }
    tasks.named("sourcesJar") {
        dependsOn("generateMRandroidMain")
    }
}