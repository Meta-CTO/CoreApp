package com.metacto.core.domain.repos

import com.metacto.core.CoreEnvironment
import com.swensonhe.strapikmm.datasource.network.services.strapi.StrapiService
import com.swensonhe.strapikmm.sharedpreference.KmmPreference

expect open class RepositoriesFactory {
    val sharedPreference: KmmPreference
    val strapiService: StrapiService
    val environment: CoreEnvironment
    val appStorageName: String
}