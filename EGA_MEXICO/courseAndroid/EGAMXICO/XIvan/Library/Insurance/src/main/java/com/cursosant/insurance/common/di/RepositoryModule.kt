package com.cursosant.insurance.common.di

import android.content.Context
import com.cursosant.insurance.common.dataAccess.MultiQuoteService
import com.cursosant.insurance.common.utils.DateUtils
import com.cursosant.insurance.deleteAccountModule.model.DeleteAccountRepository
import com.cursosant.insurance.deleteAccountModule.model.DataSource as DeleteAccountDataSource
import com.cursosant.insurance.quoteFullModule.model.DataSource as QuoteFullDataSource
import com.cursosant.insurance.quoteFullModule.model.QuoteFullRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    // --- Delete Account ---
    @Provides
    @Singleton
    fun provideDeleteAccountRepository(
        dataSource: DeleteAccountDataSource
    ): DeleteAccountRepository = DeleteAccountRepository(dataSource)

    // --- Quote Full (ajusta los params si tu constructor difiere) ---
    @Provides
    @Singleton
    fun provideQuoteFullRepository(
        @ApplicationContext context: Context,
        dataSource: QuoteFullDataSource,
        multiQuoteService: MultiQuoteService,
        date: DateUtils
    ): QuoteFullRepository = QuoteFullRepository(
        context = context,
        dataSource = dataSource,
        multiQuoteService = multiQuoteService,
        date = date
    )
}
