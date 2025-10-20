import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream
import java.io.FileOutputStream


plugins {
    alias(libs.plugins.version.catalog)
    alias(libs.plugins.maven.publish)
}

catalog {
    versionCatalog {
        from(files("../gradle/libs.versions.toml"))
    }
}

private val versionProperties = Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, Configs.VERSIONS_PROPERTIES)))
}

private val currentVersion = versionProperties.getProperty(Configs.PUBLISH_VERSION) as String

group = Configs.GROUP_ID
version = currentVersion

publishing {
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
