import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.util.Properties

abstract class UpdateVersionCatalogTask : DefaultTask() {

    /**
     * The module name to update (e.g., "core", "coreui", "files").
     * If not set, updates all modules based on versions.properties.
     */
    @get:Input
    @get:Optional
    abstract val moduleName: Property<String>

    /**
     * The version to set for the module.
     * If not set, reads from versions.properties.
     */
    @get:Input
    @get:Optional
    abstract val moduleVersion: Property<String>

    companion object {
        // Map of module names to their version key in libs.versions.toml
        val MODULE_VERSION_KEYS = mapOf(
            "core" to "metacto-core",
            "coreui" to "metacto-coreui",
            "files" to "metacto-files",
            "notifications" to "metacto-notifications",
            "phonecore" to "metacto-phonecore",
            "camera" to "metacto-camera",
            "mediaplayers" to "metacto-mediaplayers",
            "youtube" to "metacto-youtube",
            "phoneui" to "metacto-phoneui",
            "imagepicker" to "metacto-imagepicker"
        )
    }

    @TaskAction
    fun updateVersion() {
        val libsVersionsFile = File(project.rootDir, "gradle/libs.versions.toml")
        var content = libsVersionsFile.readText()

        if (moduleName.isPresent) {
            // Update single module
            val module = moduleName.get()
            val version = when {
                moduleVersion.isPresent -> moduleVersion.get()
                project.hasProperty("moduleVersion") -> project.property("moduleVersion").toString()
                else -> getVersionFromProperties("PUBLISH_${module.uppercase()}_VERSION")
                    ?: getVersionFromProperties("PUBLISH_CORE_VERSION")
                    ?: throw IllegalStateException("No version found for module: $module")
            }

            val versionKey = MODULE_VERSION_KEYS[module]
                ?: throw IllegalArgumentException("Unknown module: $module. Valid modules: ${MODULE_VERSION_KEYS.keys}")

            content = updateVersionInContent(content, versionKey, version)
            println("✅ Updated $versionKey version to $version in libs.versions.toml")
        } else {
            // Legacy behavior: update all modules with PUBLISH_CORE_VERSION
            val publishVersion = getVersionFromProperties("PUBLISH_CORE_VERSION")
                ?: throw IllegalStateException("PUBLISH_CORE_VERSION not found in versions.properties")

            MODULE_VERSION_KEYS.values.forEach { versionKey ->
                content = updateVersionInContent(content, versionKey, publishVersion)
            }
            println("✅ Updated all metacto versions to $publishVersion in libs.versions.toml")
        }

        libsVersionsFile.writeText(content)
    }

    private fun getVersionFromProperties(key: String): String? {
        val versionPropertiesFile = File(project.rootDir, "versions.properties")
        if (!versionPropertiesFile.exists()) return null

        val properties = Properties().apply {
            load(versionPropertiesFile.inputStream())
        }
        return properties.getProperty(key)
    }

    private fun updateVersionInContent(content: String, versionKey: String, version: String): String {
        return content.replace(
            Regex("""$versionKey = "[^"]+""""),
            """$versionKey = "$version""""
        )
    }
}
