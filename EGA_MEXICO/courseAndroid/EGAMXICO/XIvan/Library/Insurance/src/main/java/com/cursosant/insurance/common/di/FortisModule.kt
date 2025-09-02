package com.cursosant.insurance.common.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Empty Hilt module to avoid missing *Impl bindings.
 * DatabaseModule already provides Retrofit service interfaces.
 */
@Module
@InstallIn(SingletonComponent::class)
object FortisModule
