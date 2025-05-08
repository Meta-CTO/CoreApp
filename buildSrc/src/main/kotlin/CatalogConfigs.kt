object CatalogConfigs {
    const val FRAMEWORK_NAME = "appShared"
    const val NAMESPACE = "com.metacto.catalogapp"
    const val APP_NAME = "Catalog"
    const val DEV_APP_ID = "com.metacto.catalogapp.dev"
    const val PROD_APP_ID = "com.metacto.catalogapp.prod"
    val VERSION_CODE by lazy { getGitCommitCount() }
    val VERSION_NAME by lazy { getVersionName() }
}