package com.cursosant.insurance.quoteResultDetailModule.model

import android.content.Context
import com.cursosant.insurance.R
import com.cursosant.insurance.common.entities.QuotePayFrequency
import com.cursosant.insurance.common.entities.ServiceResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.quoteResultDetailModule.model
 * Created by Alain Nicolás Tello on 02/08/23 at 20:18
 * All rights reserved 2023.
 *
 * All my Udemy Courses:
 * https://www.udemy.com/user/alain-nicolas-tello/
 * And Frogames formación:
 * https://cursos.frogamesformacion.com/pages/instructor-alain-nicolas
 *
 * Coupons on my Website:
 * www.alainnicolastello.com
 ***/
class DataSource @Inject constructor(@ApplicationContext private val context: Context) {
    private fun getFrequenciesValue(): List<String> {
        return context.resources.getStringArray(R.array.frequencies_value).toList()
    }

    private fun getFrequenciesKey(): List<Int> {
        return context.resources.getIntArray(R.array.frequencies_key).toList()
    }

    fun getFrequencies(serviceResponse: ServiceResponse): List<QuotePayFrequency> {
        val resultKey = getFrequenciesKey()
        val payFrequencies = mutableListOf<QuotePayFrequency>()
        resultKey.forEach {
            serviceResponse.type = it.toString()
            val typeValue = getFrequenciesValue()[resultKey.indexOf(it)]
            payFrequencies.add(QuotePayFrequency(null, null, typeValue, null,
                serviceResponse.getTotalAmount(), null, null, serviceResponse.description))
        }
        return payFrequencies
    }
}