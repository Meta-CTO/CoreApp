object Configs {
    const val GROUP_ID = "com.metacto.kmm"
    const val PUBLISH_REPO_USER = "PUBLISH_REPO_USER"
    const val PUBLISH_REPO_TOKEN = "PUBLISH_REPO_TOKEN"
    const val LOCAL_PROPERTIES = "local.properties"
    const val VERSIONS_PROPERTIES = "versions.properties"
    const val PUBLISH_VERSION = "PUBLISH_CORE_VERSION"
    const val MAVEN_URL = "https://maven.pkg.github.com/Meta-CTO/coreapp"
    const val PUBLISH_MAVEN_REPO_NAME = "Github"
    const val SH_USERNAME = "developer-swensonhe"
    const val SH_PASSWORD = "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"
    val META_CTO_USERNAME: String = System.getenv("META_CTO_USERNAME").orEmpty()
    val META_CTO_PASSWORD: String = System.getenv("META_CTO_PASSWORD").orEmpty()
}