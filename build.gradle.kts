buildscript {
    repositories.addProjectRepos()

    dependencies {
        classpath(Plugins.GRADLE_CLASSPATH)
        classpath(Plugins.KOTLIN_CLASSPATH)
        classpath(Plugins.SERIALIZATION_CLASSPATH)
        classpath(Plugins.PLAY_SERVICES_CLASSPATH)
        classpath(Plugins.CRASHLYTICS_PLUGIN_CLASSPATH)
    }
}

allprojects {
    repositories.addProjectRepos()
}
