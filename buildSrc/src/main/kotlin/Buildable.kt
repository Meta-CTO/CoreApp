import java.util.*

sealed class Buildable {
    val name: String = this.javaClass.simpleName.lowercase(Locale.US)
    abstract val isDebuggable: Boolean
    abstract val isMinifyEnabled: Boolean
    abstract val isShrinkResources: Boolean
    abstract val storeFile: String
    abstract val keyAlias: String
    abstract val keyPassword: String
    abstract val storePassword: String

    object Debug : Buildable() {
        override val isDebuggable: Boolean = true
        override val isMinifyEnabled: Boolean = false
        override val isShrinkResources: Boolean = false
        override val storeFile: String = "./signing/debug.keystore"
        override val keyAlias: String = "androiddebugkey"
        override val keyPassword: String = "android"
        override val storePassword: String = "android"
    }

    object Release : Buildable() {
        override val isDebuggable: Boolean = false
        override val isMinifyEnabled: Boolean = false
        override val isShrinkResources: Boolean = false
        override val storeFile: String = "./signing/release.keystore"
        override val keyAlias: String = "myatlas"
        override val keyPassword: String = "myatlas"
        override val storePassword: String = "myatlas"
    }

    companion object {
        fun all() = arrayListOf(Debug, Release)
    }
}