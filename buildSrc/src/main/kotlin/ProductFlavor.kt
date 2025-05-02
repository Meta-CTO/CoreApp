import java.util.*

sealed class ProductFlavor {
    val name: String = javaClass.simpleName.lowercase(Locale.US)
    abstract val applicationId: String
    open val appNameSuffix: String = ""
    open val versionNameSuffix: String = ""

    object QA : ProductFlavor() {
        override val applicationId: String = CatalogAppConfigs.DEV_APP_ID
        override val appNameSuffix: String = "-DEV"
        override val versionNameSuffix: String = "-dev"
    }

    object PROD : ProductFlavor() {
        override val applicationId: String = CatalogAppConfigs.PROD_APP_ID
    }

    companion object {
        fun all() = arrayListOf(QA, PROD)
    }

    override fun toString(): String = name
}

object FlavorDimensions {
    const val DEFAULT = "default"
}
