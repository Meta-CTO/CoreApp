import java.util.*

sealed class ProductFlavor {
    val name: String = javaClass.simpleName.lowercase(Locale.US)
    open val appNameSuffix: String = ""
    open val applicationIdSuffix: String = ""
    open val versionNameSuffix: String = ""

    object QA : ProductFlavor() {
        override val appNameSuffix: String = "-DEV"
        override val applicationIdSuffix: String = ".dev"
        override val versionNameSuffix: String = "-dev"
    }

    object PROD : ProductFlavor()

    companion object {
        fun all() = arrayListOf(QA, PROD)
    }

    override fun toString(): String = name
}

object FlavorDimensions {
    const val DEFAULT = "default"
}
