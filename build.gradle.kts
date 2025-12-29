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

// Legacy task: updates all modules with PUBLISH_CORE_VERSION
tasks.register<UpdateVersionCatalogTask>("updateVersionCatalog") {
    group = "publishing"
    description = "Updates all metacto versions in libs.versions.toml from versions.properties"
}

// Per-module version update tasks
val modules = listOf(
    "core", "coreui", "files", "notifications", "phonecore",
    "camera", "mediaplayers", "youtube", "phoneui", "imagepicker"
)

modules.forEach { module ->
    tasks.register<UpdateVersionCatalogTask>("updateVersionCatalog_$module") {
        group = "publishing"
        description = "Updates metacto-$module version in libs.versions.toml"
        moduleName.set(module)
    }
}

// Convenience task to update version catalog and publish
tasks.register("publishVersionCatalog") {
    group = "publishing"
    description = "Updates version catalog and publishes to Maven"
    dependsOn("updateVersionCatalog")
    finalizedBy(":version-catalog:publishToMavenLocal")
}
