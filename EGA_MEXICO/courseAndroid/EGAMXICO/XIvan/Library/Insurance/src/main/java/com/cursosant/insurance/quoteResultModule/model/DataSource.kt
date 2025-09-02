package com.cursosant.insurance.quoteResultModule.model

import android.content.Context
import android.util.ArrayMap
import com.cursosant.insurance.R
import com.cursosant.insurance.common.dataAccess.MultiQuoteService
import com.cursosant.insurance.common.entities.ServiceResponse
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
    fun getCoverages(): List<String> {
        return context.resources.getStringArray(R.array.coverages_value).toList()
    }

    fun getFrequencies(): List<String> {
        return context.resources.getStringArray(R.array.frequencies_value).toList()
    }

    suspend fun getService(
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
        email: String,
        xml: String?,
        coverage: String?,
        formDataId: Long?,
        service: Int
    ): ServiceResponse {
        val startDate = date.formatDate(Constants.DATE_PATTERN_VALIDITY)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.YEAR, 1)
        val endDate = date.formatDate(Constants.DATE_PATTERN_VALIDITY, calendar.timeInMillis)

        val params: MutableMap<String?, Any?> = ArrayMap()
        params[Constants.P_ADDRESS_ONE] = ""
        params[Constants.P_ADDRESS_TWO] = suburb
        params[Constants.P_BIRTHDATE] = birthdate
        params[Constants.P_CAR_BD] = Constants.V_CAR_BD
        params[Constants.P_CAR_BRAND] = brand
        params[Constants.P_CAR_CLASS] = typeId
        params[Constants.P_CAR_MODEL_ID] = modelId
        params[Constants.P_CAR_SERIAL] = Constants.V_CAR_SERIAL
        params[Constants.P_CAR_SEX] = gender
        params[Constants.P_CAR_YEAR] = year
        params[Constants.P_CIVIL_STATE] = Constants.V_CIVIL_STATE.toInt()
        params[Constants.P_COB] = coverage
        params[Constants.P_COUNTRY] = Constants.V_COUNTRY
        params[Constants.P_DD_STATE] = state
        params[Constants.P_DD_CITY] = city
        params[Constants.P_EMAIL] = email
        params[Constants.P_STATE_ABA_ID] = Constants.V_STATE_ABA_ID.toInt()
        params[Constants.P_Q_STATE_ID] = stateId
        params[Constants.P_END_VALIDITY] = endDate
        params[Constants.P_FIRST_NAME] = Constants.V_FIRST_NAME
        params[Constants.P_START_VALIDITY] = startDate
        params[Constants.P_LADA] = Constants.V_LADA
        params[Constants.P_LAST_NAME] = Constants.V_LAST_NAME
        params[Constants.P_MODEL_NAME] = model
        params[Constants.P_PHONE] = Constants.V_PHONE
        params[Constants.P_Q_FREQUENCY] = Constants.V_Q_FREQUENCY.toInt()
        params[Constants.P_Q_PAYMENT_METHOD] = ""
        params[Constants.P_RFC] = Constants.V_RFC
        params[Constants.P_SECOND_LAST_NAME] = Constants.V_SECOND_LAST_NAME
        params[Constants.P_SERVICES] = ""
        params[Constants.P_STATE_NAME] = state
        params[Constants.P_URL_NAME] = Constants.V_URL_NAME
        params[Constants.P_XML] = xml
        params[Constants.P_ZIP_CODE] = cp
        params[Constants.P_FORM_DATA_ID] = formDataId
        params[Constants.P_SERVICE] = service

        val body = JSONObject(params)
            .toString()
            .toRequestBody("application/json; charset=utf-8".toMediaType())

        return multiQuoteService.getService("${Constants.H_BEARER}$token", service.toString(), body)
    }
}
