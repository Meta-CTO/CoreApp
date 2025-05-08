buildscript {
    repositories.addProjectRepos()

    dependencies {
        classpath(libs.classpath.androidgradle)
        classpath(libs.classpath.kotlingradle)
        classpath(libs.classpath.serialization)
        classpath(libs.classpath.googleservices)
        classpath(libs.classpath.crashlytics)
    }
}

allprojects {
    repositories.addProjectRepos()
}
