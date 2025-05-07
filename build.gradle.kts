buildscript {
    repositories.addProjectRepos()

    dependencies {
        classpath(libs.android.gradle.classpath)
        classpath(libs.kotlin.gradle.classpath)
        classpath(libs.kotlin.serialization.classpath)
        classpath(libs.google.services.classpath)
        classpath(libs.crashlytics.gradle.classpath)
    }
}

allprojects {
    repositories.addProjectRepos()
}
