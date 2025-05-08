import java.util.*

sealed class CatalogProductFlavor {
    abstract val applicationId: String
    abstract val appName: String
    open val versionNameSuffix: String = ""

    object QA : CatalogProductFlavor() {
        override val applicationId: String = CatalogConfigs.DEV_APP_ID
        override val appName: String = "${CatalogConfigs.APP_NAME}-DEV"
        override val versionNameSuffix: String = "-dev"
    }

    object PROD : CatalogProductFlavor() {
        override val applicationId: String = CatalogConfigs.PROD_APP_ID
        override val appName: String = CatalogConfigs.APP_NAME
    }

    companion object {
        fun all() = listOf(QA, PROD)
    }

    override fun toString(): String = javaClass.simpleName.lowercase(Locale.US)
}