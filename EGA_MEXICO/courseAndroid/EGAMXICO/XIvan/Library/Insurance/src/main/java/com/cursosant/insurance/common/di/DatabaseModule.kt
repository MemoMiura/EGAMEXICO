package com.cursosant.insurance.common.di

import com.cursosant.insurance.common.dataAccess.UserService
import com.cursosant.insurance.common.dataAccess.MultiQuoteService
import com.cursosant.insurance.common.dataAccess.MiuraboxService
import com.cursosant.insurance.common.entities.Aseguradora
import com.cursosant.insurance.common.entities.deserializers.AseguradoraDeserializer
import com.cursosant.insurance.common.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(Aseguradora::class.java, AseguradoraDeserializer())
            .create()
    }

    @Provides
    @Singleton
    @Named("UserRetrofit")
    fun provideUserRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_USER_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    @Named("MiuraboxRetrofit")
    fun provideMiuraboxRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_MIURABOX_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    @Named("MultiQuoteRetrofit")
    fun provideMultiQuoteRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_MULTI_QUOTE)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
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
