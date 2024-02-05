package com.metacto.core.utils.remoteConfigs

interface IRemoteConfigs {
    suspend fun init(minFetchIntervalSeconds: Long = DEFAULT_MIN_FETCH_INTERVAL_SECONDS)
    fun getString(key: String): String?
    fun getBoolean(key: String): Boolean?
    fun getDouble(key: String): Double?
    fun getLong(key: String): Long?
    fun getInt(key: String): Int?

    companion object {
        private const val DEFAULT_MIN_FETCH_INTERVAL_SECONDS = 0L
    }
}
