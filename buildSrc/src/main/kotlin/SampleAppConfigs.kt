object SampleAppConfigs {
    const val FRAMEWORK_NAME = "sampleAppShared"
    const val NAMESPACE = "com.sampleApp.app"
    const val DEV_APP_ID = "com.sampleApp.app.dev"
    const val PROD_APP_ID = "com.sampleApp.app.prod"
    val VERSION_CODE by lazy { getGitCommitCount() }
    val VERSION_NAME by lazy { getVersionName() }
}