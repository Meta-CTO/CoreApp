package com.metacto.core.utils.remoteConfigs

interface IRemoteConfigs {
    suspend fun init(minFetchIntervalSeconds: Long = DEFAULT_MIN_FETCH_INTERVAL_SECONDS)

    fun getString(key: String): String?
    suspend fun forceGetString(key: String): String?

    fun getBoolean(key: String): Boolean?
    suspend fun forceGetBoolean(key: String): Boolean?

    fun getDouble(key: String): Double?
    suspend fun forceGetDouble(key: String): Double?

    fun getLong(key: String): Long?
    suspend fun forceGetLong(key: String): Long?

    fun getInt(key: String): Int?
    suspend fun forceGetInt(key: String): Int?

    companion object {
        private const val DEFAULT_MIN_FETCH_INTERVAL_SECONDS = 0L
    }
}
