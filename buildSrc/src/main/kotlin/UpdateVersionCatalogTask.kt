import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.util.Properties

open class UpdateVersionCatalogTask : DefaultTask() {

    @TaskAction
    fun updateVersion() {
        val versionProperties = Properties().apply {
            load(File(project.rootDir, "versions.properties").inputStream())
        }
        val publishVersion = versionProperties.getProperty("PUBLISH_CORE_VERSION")

        val libsVersionsFile = File(project.rootDir, "gradle/libs.versions.toml")
        val content = libsVersionsFile.readText()

        // Update metacto-core version
        val updatedContent = content.replace(
            Regex("""metacto-core = "[^"]+""""),
            """metacto-core = "$publishVersion""""
        )

        libsVersionsFile.writeText(updatedContent)

        println("✅ Updated metacto-core version to $publishVersion in libs.versions.toml")
    }
}
