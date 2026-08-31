package com.metacto.core.domain.repos

import com.metacto.core.CoreConfigs
import com.metacto.kmm.network.services.HttpService
import com.metacto.kmm.sharedpreferences.KmmPreference

expect open class RepositoriesFactory<T : Any> {
    val sharedPreference: KmmPreference
    val httpService: HttpService
    val coreConfigs: CoreConfigs
    val appStorageName: String
    val shouldShowActualErrorMessages: Boolean
    val networkUserAgent: String?
    val errorHandling: ApiErrorHandling<T>
}
