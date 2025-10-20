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

tasks.register<UpdateVersionCatalogTask>("updateVersionCatalog") {
    group = "publishing"
    description = "Updates metacto-core version in libs.versions.toml from versions.properties"
}

// Convenience task to update version catalog and publish
tasks.register("publishVersionCatalog") {
    group = "publishing"
    description = "Updates version catalog and publishes to Maven"
    dependsOn("updateVersionCatalog")
    finalizedBy(":version-catalog:publishToMavenLocal")
}
