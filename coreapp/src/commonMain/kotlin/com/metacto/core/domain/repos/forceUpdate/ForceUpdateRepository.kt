package com.metacto.core.domain.repos.forceUpdate

import com.metacto.core.CoreEnvironment
import com.metacto.core.domain.repos.forceUpdate.AppUpdateSource.*
import com.metacto.core.utils.remoteConfigs.IRemoteConfigs
import com.metacto.strapikmm.appconfigversion.AppConfigurationVersion
import com.metacto.strapikmm.appconfigversion.AppVersion
import com.metacto.strapikmm.appconfigversion.AppVersionValidator
import com.metacto.strapikmm.appconfigversion.UpdateType
import com.metacto.strapikmm.repos.AppConfigurationRepository
import kotlinx.serialization.json.Json

class ForceUpdateRepository(
    private val appEnvironment: CoreEnvironment,
    private val appConfigurationRepository: AppConfigurationRepository,
    private val remoteConfigs: IRemoteConfigs,
    private val applicationContext: Any? = null,
) {
    @Throws(Throwable::class)
    suspend fun checkForceUpdate(
        appUpdateSource: AppUpdateSource,
        onUpdateRequired: (message: String, isRequired: Boolean, iosAppStoreId: String) -> Unit,
        onProceed: () -> Unit
    ) {
        val appUpdateResult = when (appUpdateSource) {
            REMOTE_CONFIGS -> {
                // fetch the remote config
                val forceUpdateRemoteConfig = remoteConfigs.forceGetString(
                    appEnvironment.forceUpdateRemoteConfigKey
                )

                // start handle the force update from remote config
                val forceUpdate = forceUpdateRemoteConfig?.let {
                    Json.decodeFromString<List<AppVersion>>(it)
                }

                // return the validation
                AppVersionValidator.checkRequiredUpdate(forceUpdate.orEmpty(), applicationContext)
            }

            STRAPI_CONFIGS -> {
                appConfigurationRepository.checkAppUpdates<AppConfigurationVersion>(
                    appConfigurationQueryBuilder = { populate("*") },
                    currentAppConfigurationVersion = 1
                )
            }
        }

        // Check the force update type and return the required action
        when (appUpdateResult.updateType) {
            UpdateType.REQUIRED,
            UpdateType.OPTIONAL -> {
                val isRequired = appUpdateResult.updateType == UpdateType.REQUIRED
                onUpdateRequired.invoke(
                    appUpdateResult.message,
                    isRequired,
                    appEnvironment.iosAppStoreId
                )
            }

            UpdateType.NONE -> {
                onProceed.invoke()
            }
        }
    }
}