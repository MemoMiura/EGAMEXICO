package com.cursosant.insurance.quoteFullModule.model

import android.content.Context
import com.cursosant.insurance.R
import com.cursosant.insurance.common.dataAccess.MultiQuoteService
import com.cursosant.insurance.common.entities.CP
import com.cursosant.insurance.common.entities.QuoteBasic
import com.cursosant.insurance.common.entities.QuoteResponse
import com.cursosant.insurance.common.entities.State
import com.cursosant.insurance.common.entities.SuburbsResponse
import com.cursosant.insurance.common.utils.DateUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class QuoteFullRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataSource: DataSource,
    private val multiQuoteService: MultiQuoteService,
    private val date: DateUtils
) {
    fun getGenders(): List<String> {
        return context.resources.getStringArray(R.array.genders_value).toList()
    }

    suspend fun getTypes(): Map<Int, String> {
        return multiQuoteService.getTypes()
    }

    suspend fun getBrands(type: String?): List<String> {
        return multiQuoteService.getBrands(type ?: "").map { it.name }
    }

    suspend fun getYears(type: String?, brand: String?): List<String> {
        return multiQuoteService.getYears(type ?: "", brand ?: "").map { it.name }
    }

    suspend fun getModels(type: String?, brand: String?, year: String?): List<QuoteBasic> {
        val result = multiQuoteService.getModels(type ?: "", brand ?: "", year ?: "").values
        val models: MutableList<QuoteBasic> = mutableListOf()
        result.forEach { items ->
            items.forEach { models.add(it) }
        }
        return models
    }

    suspend fun getStates(token: String): List<State> {
        return multiQuoteService.getStates("Bearer $token").states
    }

    suspend fun getCities(token: String, stateId: Long?): List<String> {
        return multiQuoteService.getCities("Bearer $token", stateId.toString()).map { it.name }
    }

    suspend fun getSuburbs(token: String, stateId: Long?, city: String?): List<String> {
        return multiQuoteService.getSuburbs("Bearer $token", stateId.toString(), city ?: "").map { it.name }
    }

    suspend fun getCP(token: String, stateId: Long?, city: String?, suburb: String?): CP {
        return multiQuoteService.getCP("Bearer $token", stateId.toString(), city ?: "", suburb ?: "")
    }

    suspend fun getSuburbsByCP(token: String, cp: String?): SuburbsResponse {
        return multiQuoteService.getSuburbsByCP("Bearer $token", cp ?: "")
    }

    fun getCoverages(): List<String> {
        return context.resources.getStringArray(R.array.coverages_value).toList()
    }

    fun getFrequencies(): List<String> {
        return context.resources.getStringArray(R.array.frequencies_value).toList()
    }

    suspend fun getQuote(
        token: String,
        gender: String?,
        birthdate: String?,
        typeId: Int,
        brand: String?,
        year: String?,
        modelId: String?,
        model: String?,
        cp: String?,
        stateId: Long?,
        state: String?,
        city: String?,
        suburb: String?,
        email: String
    ): QuoteResponse {
        val params = mapOf(
            "gender" to gender,
            "birthdate" to birthdate,
            "typeId" to typeId,
            "brand" to brand,
            "year" to year,
            "modelId" to modelId,
            "model" to model,
            "cp" to cp,
            "stateId" to stateId,
            "state" to state,
            "city" to city,
            "suburb" to suburb,
            "email" to email
        )
        return dataSource.getQuote(token, params)
    }
}
