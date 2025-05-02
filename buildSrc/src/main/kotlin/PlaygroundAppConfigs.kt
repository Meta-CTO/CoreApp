object PlaygroundAppConfigs {
    const val FRAMEWORK_NAME = "appShared"
    const val NAMESPACE = "com.metacto.playground"
    const val DEV_APP_ID = "com.metacto.playground.dev"
    const val PROD_APP_ID = "com.metacto.playground.prod"
    val VERSION_CODE by lazy { getGitCommitCount() }
    val VERSION_NAME by lazy { getVersionName() }
}