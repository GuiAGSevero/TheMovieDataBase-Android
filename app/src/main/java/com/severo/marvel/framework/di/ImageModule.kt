package com.severo.marvel.framework.di

import com.severo.marvel.framework.imageloader.GlideImageLoader
import com.severo.marvel.framework.imageloader.ImageLoader
import org.koin.dsl.module

val imageModule = module {
    factory<ImageLoader> { GlideImageLoader() }
}