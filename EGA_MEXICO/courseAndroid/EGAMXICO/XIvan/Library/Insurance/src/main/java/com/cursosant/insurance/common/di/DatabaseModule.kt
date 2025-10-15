package com.cursosant.insurance.common.di

import com.cursosant.insurance.common.dataAccess.UserService
import com.cursosant.insurance.common.dataAccess.MultiQuoteService
import com.cursosant.insurance.common.dataAccess.MiuraboxService
import com.cursosant.insurance.common.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    @Named("UserRetrofit")
    fun provideUserRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_USER_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("MiuraboxRetrofit")
    fun provideMiuraboxRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_MIURABOX_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("MultiQuoteRetrofit")
    fun provideMultiQuoteRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_MULTI_QUOTE)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideUserService(@Named("UserRetrofit") retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideMultiQuoteService(@Named("MultiQuoteRetrofit") retrofit: Retrofit): MultiQuoteService =
        retrofit.create(MultiQuoteService::class.java)

    @Provides
    @Singleton
    fun provideMiuraboxService(@Named("MiuraboxRetrofit") retrofit: Retrofit): MiuraboxService =
        retrofit.create(MiuraboxService::class.java)
}
