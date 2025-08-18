import java.util.*

sealed class PlaygroundProductFlavor {
    abstract val applicationId: String
    abstract val appName: String
    open val versionNameSuffix: String = ""

    object QA : PlaygroundProductFlavor() {
        override val applicationId: String = PlaygroundConfigs.DEV_APP_ID
        override val appName: String = "${PlaygroundConfigs.APP_NAME}-DEV"
        override val versionNameSuffix: String = "-dev"
    }

    object PROD : PlaygroundProductFlavor() {
        override val applicationId: String = PlaygroundConfigs.PROD_APP_ID
        override val appName: String = PlaygroundConfigs.APP_NAME
    }

    companion object {
        fun all() = listOf(QA, PROD)
    }

    override fun toString(): String = javaClass.simpleName.lowercase(Locale.US)
}