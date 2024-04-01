import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

fun RepositoryHandler.addProjectRepos() {
    gradlePluginPortal()
    google()
    jcenter()
    mavenCentral()
    mavenLocal()
    maven("https://jitpack.io")
    maven("https://storage.googleapis.com/r8-releases/raw")
    maven("https://maven.google.com")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://plugins.gradle.org/m2/")
    maven("https://maven.pkg.github.com/swensonhe/firebase-kotlin-sdk") {
        credentials {
            username = Configs.SH_USERNAME
            password = Configs.SH_PASSWORD
        }
    }
    maven("https://maven.pkg.github.com/Meta-CTO/strapi-kmm") {
        credentials {
            username = Configs.META_CTO_USERNAME
            password = Configs.META_CTO_PASSWORD
        }
    }
}