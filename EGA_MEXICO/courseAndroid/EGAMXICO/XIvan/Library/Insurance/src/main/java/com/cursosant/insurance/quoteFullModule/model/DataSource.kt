package com.cursosant.insurance.quoteFullModule.model

import android.content.Context
import android.util.ArrayMap
import com.cursosant.insurance.common.dataAccess.MultiQuoteService
import com.cursosant.insurance.common.entities.QuoteResponse
import com.cursosant.insurance.common.utils.Constants
import com.cursosant.insurance.common.utils.DateUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.Calendar
import javax.inject.Inject

class DataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val multiQuoteService: MultiQuoteService,
    private val date: DateUtils
) {
    suspend fun getQuote(
        token: String,
        paramsExtra: Map<String, Any?>
    ): QuoteResponse {
        val startDate = date.formatDate(Constants.DATE_PATTERN_VALIDITY)
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.YEAR, 1)
        val endDate = date.formatDate(Constants.DATE_PATTERN_VALIDITY, calendar.timeInMillis)

        val params: MutableMap<String?, Any?> = ArrayMap()
        params.putAll(paramsExtra)
        params[Constants.P_START_VALIDITY] = startDate
        params[Constants.P_END_VALIDITY] = endDate

        val body = JSONObject(params)
            .toString()
            .toRequestBody("application/json; charset=utf-8".toMediaType())

        return multiQuoteService.getQuote("${Constants.H_BEARER}$token", body)
    }
}
