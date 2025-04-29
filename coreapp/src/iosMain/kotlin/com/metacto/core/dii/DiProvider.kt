package com.metacto.core.dii

import org.koin.core.component.KoinComponent
import kotlin.reflect.KClass


object DiProvider : KoinComponent {
    private val koin = getKoin()

    fun getDependency(clazz: KClass<*>): Any {
        return koin.get(clazz, null, null)
    }
}