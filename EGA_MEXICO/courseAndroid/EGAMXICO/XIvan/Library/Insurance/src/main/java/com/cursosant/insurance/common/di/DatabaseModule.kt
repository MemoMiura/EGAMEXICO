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
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL) // 👈 asegúrate de que BASE_URL está en Constants
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideMultiQuoteService(retrofit: Retrofit): MultiQuoteService =
        retrofit.create(MultiQuoteService::class.java)

    @Provides
    @Singleton
    fun provideMiuraboxService(retrofit: Retrofit): MiuraboxService =
        retrofit.create(MiuraboxService::class.java)
}
