package com.metacto.core.domain.repos

import com.metacto.core.CoreEnvironment
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
        isFromRemoteConfigs: Boolean,
        onUpdateRequired: (message: String, isRequired: Boolean, iOSAppVersion: String) -> Unit,
        onProceed: () -> Unit
    ) {

        // check if the remote config has
        val appUpdateResult = if (isFromRemoteConfigs) {
            // fetch the remote config
            val forceUpdateRemoteConfig =
                remoteConfigs.forceGetString(appEnvironment.updateRemoteConfigKey)

            // start handle the force update from remote config
            val forceUpdate =
                forceUpdateRemoteConfig?.let { Json.decodeFromString<List<AppVersion>>(it) }

            // return the validation
            AppVersionValidator.checkRequiredUpdate(forceUpdate.orEmpty(), applicationContext)

        } else {
            // check the app versions from the app config
            appConfigurationRepository.checkAppUpdates<AppConfigurationVersion>(
                appConfigurationQueryBuilder = { populate("*") },
                1
            )
        }

        // check the force update type and return the required action
        when (appUpdateResult.updateType) {
            UpdateType.REQUIRED, UpdateType.OPTIONAL -> onUpdateRequired(
                appUpdateResult.message,
                appUpdateResult.updateType == UpdateType.REQUIRED,
                appEnvironment.iOSAppId
            )

            UpdateType.NONE -> onProceed()
        }
    }
}