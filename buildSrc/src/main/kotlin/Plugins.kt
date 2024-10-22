object Plugins {
    // Plugins
    const val MULTIPLATFORM_PLUGIN = "multiplatform"
    const val COCOAPODS_PLUGIN = "native.cocoapods"
    const val ANDROID_LIBRARY_PLUGIN = "com.android.library"
    const val COMPOSE_PLUGIN = "org.jetbrains.compose"
    const val COMPOSE_COMPILER_PLUGIN = "org.jetbrains.kotlin.plugin.compose"
    const val SERIALIZATION_PLUGIN = "org.jetbrains.kotlin.plugin.serialization"
    const val PARCELIZE_PLUGIN = "kotlin-parcelize"
    const val ANDROID_APPLICATION_PLUGIN = "com.android.application"
    const val PLAY_SERVICES_PLUGIN = "com.google.gms.google-services"
    const val CRASHLYTICS_PLUGIN = "com.google.firebase.crashlytics"
    const val KT_LINT_PLUGIN = "org.jlleitschuh.gradle.ktlint"
    const val DETEKT_PLUGIN = "io.gitlab.arturbosch.detekt"
    const val BUILD_CONFIGS_PLUGIN = "com.github.gmazzo.buildconfig"
    const val SWIFT_KLIB = "io.github.ttypic.swiftklib"
    const val MAVEN_PUBLISH = "maven-publish"
    const val SIGNING = "signing"

    // Classpaths
    const val GRADLE_CLASSPATH = "com.android.tools.build:gradle:${Versions.GRADLE}"
    const val KOTLIN_CLASSPATH = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
    const val SERIALIZATION_CLASSPATH =
        "org.jetbrains.kotlin:kotlin-serialization:${Versions.SERIALIZATION}"
    const val PLAY_SERVICES_CLASSPATH =
        "com.google.gms:google-services:${Versions.PLAY_SERVICES_PLUGIN}"
    const val CRASHLYTICS_PLUGIN_CLASSPATH =
        "com.google.firebase:firebase-crashlytics-gradle:${Versions.CRASHLYTICS_PLUGIN}"
}