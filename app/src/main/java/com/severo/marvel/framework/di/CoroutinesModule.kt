package com.severo.marvel.framework.di

import com.severo.core.usecase.base.AppCoroutinesDispatchers
import com.severo.core.usecase.base.CoroutinesDispatchers
import org.koin.dsl.module

val coroutinesModule = module {
    single<CoroutinesDispatchers> { AppCoroutinesDispatchers() }
}