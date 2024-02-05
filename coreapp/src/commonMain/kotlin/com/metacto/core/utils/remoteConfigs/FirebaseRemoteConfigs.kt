package com.metacto.core.utils.remoteConfigs

import com.metacto.core.domain.CoreConstants.CACHED_REMOTE_CONFIGS
import com.metacto.core.utils.extensions.toJsonObject
import com.metacto.core.utils.extensions.toPairs
import com.swensonhe.strapikmm.sharedpreference.KmmPreference
import com.swensonhe.strapikmm.util.Logger
import dev.gitlive.firebase.remoteconfig.FirebaseRemoteConfig
import dev.gitlive.firebase.remoteconfig.get
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import com.metacto.core.utils.extensions.putObject

class FirebaseRemoteConfigs(
    private val kmmPreference: KmmPreference,
    private val firebaseConfigs: FirebaseRemoteConfig
) : IRemoteConfigs {

    private val logger = Logger(LOG_TAG)

    override suspend fun init(minFetchIntervalSeconds: Long) {
        setSettings(minFetchIntervalSeconds)
        loadDefaults()
        fetchConfigsFromRemote()
    }

    private suspend fun setSettings(minimumFetchIntervalSeconds: Long) {
        try {
            firebaseConfigs.settings {
                this.minimumFetchIntervalInSeconds = minimumFetchIntervalSeconds
            }
            logger.log("Updated settings")
        } catch (e: Throwable) {
            logger.log("Error: (${e.message})")
        }
    }

    private suspend fun loadDefaults() {
        try {
            // Get and validate cached configs
            val cachedConfigs = kmmPreference.getString(CACHED_REMOTE_CONFIGS) ?: return
            val configsObject = Json.parseToJsonElement(cachedConfigs).jsonObject

            // Set defaults
            firebaseConfigs.setDefaults(
                *configsObject.toPairs().toTypedArray()
            )

            logger.log("Loaded defaults ($cachedConfigs)")
        } catch (e: Throwable) {
            logger.log("Error: (${e.message})")
        }
    }

    private suspend fun fetchConfigsFromRemote() {
        try {
            // Fetch
            firebaseConfigs.fetchAndActivate()
            val updatedConfigs = firebaseConfigs.all
                .map { (key, value) -> key to value.asString() }
                .toMap()
                .toJsonObject()

            // Then cache it
            kmmPreference.putObject(CACHED_REMOTE_CONFIGS, updatedConfigs)

            logger.log("Updated configs from remote ($updatedConfigs)")
        } catch (e: Throwable) {
            logger.log("Error: (${e.message})")
        }
    }

    override fun getString(key: String): String? {
        return try {
            firebaseConfigs[key]
        } catch (e: Throwable) {
            null
        }
    }

    override fun getBoolean(key: String): Boolean? {
        return try {
            firebaseConfigs[key]
        } catch (e: Throwable) {
            null
        }
    }

    override fun getDouble(key: String): Double? {
        return try {
            firebaseConfigs[key]
        } catch (e: Throwable) {
            null
        }
    }

    override fun getLong(key: String): Long? {
        return try {
            firebaseConfigs[key]
        } catch (e: Throwable) {
            null
        }
    }

    override fun getInt(key: String): Int? {
        return try {
            firebaseConfigs[key]
        } catch (e: Throwable) {
            null
        }
    }

    companion object {
        private const val LOG_TAG = "RemoteConfigs"
    }
}
