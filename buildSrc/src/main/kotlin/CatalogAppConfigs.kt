object CatalogAppConfigs {
    const val FRAMEWORK_NAME = "appShared"
    const val NAMESPACE = "com.metacto.sampleapp"
    const val DEV_APP_ID = "com.metacto.sampleapp.dev"
    const val PROD_APP_ID = "com.metacto.sampleapp.prod"
    val VERSION_CODE by lazy { getGitCommitCount() }
    val VERSION_NAME by lazy { getVersionName() }
}