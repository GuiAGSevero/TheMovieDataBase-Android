package com.severo.marvel.framework.di

import com.severo.core.data.mapper.SortingMapper
import org.koin.dsl.module

val sortingMapperModule = module {
    single { SortingMapper() }
}